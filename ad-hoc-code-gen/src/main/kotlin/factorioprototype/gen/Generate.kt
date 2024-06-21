package factorioprototype.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull

public const val packageName = "factorioprototype"

class DeclarationsGenerator(private val docs: ApiDocs) {
    init {
        require(
            docs.application == "factorio" &&
                    docs.api_version == 5 &&
                    docs.stage == "prototype"
        )
    }

    private val byName = (docs.prototypes + docs.types).associateBy { it.name }
    private val file: FileSpec.Builder = FileSpec.builder("factorioprototype", "FactorioPrototypes")

    fun generate(): FileSpec {
        file.addFileComment("Automatically generated file, do not edit")
        file.addKotlinDefaultImports()
        findOverriddes()
        docs.prototypes
            .sortedBy { it.order }
            .forEach { generatePrototype(it) }
        docs.types
            .sortedBy { it.order }
            .forEach { generateTypeInterface(it) }

        generatePrototypeDataClass()
        return file.build()
    }

    private tailrec fun getReferencedType(type: TypeDefinition): String? {
        val innerType = type.innerType()
        if (innerType is BasicType) {
            val value = innerType.value
            if (value in builtins) return value
            val concept = byName[value]
            if (concept is Concept) {
                return getReferencedType(concept.type)
            }
            return value
        }
        return null
    }

    private enum class OverrideType {
        Ignore,
        Unknown,
        AsNullable,
        Allowed
    }

    private val overriddes = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()
    private val childOverrides = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()
    private fun findOverriddes() {
        tailrec fun findParentProp(
            prototype: ProtoOrConcept,
            propName: String
        ): Pair<ProtoOrConcept, Property> {
            prototype.properties!!.find { it.name == propName }?.let { return prototype to it }
            return findParentProp(
                byName[prototype.parent!!]!!,
                propName
            )
        }

        val visited = mutableSetOf<String>()
        fun visit(prototype: ProtoOrConcept) {
            if (prototype.name in visited) return
            visited.add(prototype.name)

            val parentName = prototype.parent ?: return
            val parent = byName[parentName]!!
            visit(parent)
            val properties = prototype.properties ?: return
            for (prop in properties.filter { it.override }) {
                val (source, parentProp) = findParentProp(parent, prop.name)
                val overrideType: OverrideType = if (prop.type.typeEquals(parentProp.type)) {
                    if (prop.optional && !parentProp.optional) {
                        OverrideType.AsNullable
                    } else if (prop.optional == parentProp.optional && overriddes[parent]?.get(prop.name) == OverrideType.Ignore) {
                        OverrideType.Ignore
                    } else {
                        OverrideType.Allowed
                    }
                } else if (parentProp.type.isNumeric() && prop.type.isNumeric()
                    || parentProp.type.isBoolish() && prop.type.isBoolish()
                    || parentProp.type.isString() && prop.type.isString()
                ) {
                    OverrideType.Ignore
                } else if (
                    prototype.name == "TurretPrototype" &&
                    prop.name == "corpse"
                ) {
                    OverrideType.Ignore
                } else {
                    OverrideType.Unknown
                }
                overriddes.getOrPut(prototype, ::mutableMapOf)[prop.name] = overrideType
                childOverrides.getOrPut(source, ::mutableMapOf)[prop.name] = overrideType
            }

        }
        for (value in byName.values) {
            visit(value)
        }
    }

    private fun TypeSpec.Builder.commonSetup(value: ProtoOrConcept) {
        if (value.abstract) {
            addModifiers(KModifier.ABSTRACT)
        } else {
            addModifiers(KModifier.OPEN)
        }
        if (value.parent != null) {
            superclass(ClassName(packageName, value.parent!!))
        } else {
            superclass(ClassName(packageName, "JsonReader"))
        }
        if (value.description.isNotEmpty()) {
            addDescription(value.description)
        }
        addType(TypeSpec.companionObjectBuilder().apply {
            if (!value.abstract) addSerializerMethod(this@commonSetup.build().name!!)
        }.build())
    }

    private fun TypeSpec.Builder.addSerializerMethod(className: String) {
        addFunction(FunSpec.builder("serializer").apply {
            returns(
                KSerializer::class.asClassName()
                    .parameterizedBy(ClassName(packageName, className))
            )
            addStatement("return JsonReaderDeserializer()")
        }.build())
    }

    private fun generatePrototype(prototype: Prototype) {
        require(prototype.visibility == null)
        val result = TypeSpec.classBuilder(prototype.name).apply {
            commonSetup(prototype)
            for (property in prototype.properties.sortedBy { it.order }) {
                if (overriddes[prototype]?.get(property.name) == OverrideType.Ignore) continue
                addProperty(mapProperty(prototype, property))
            }
        }.build()
        file.addType(result)
    }

    private fun generateTypeInterface(concept: Concept) {
        if (concept.name in builtins || concept.name in toIgnore) return
        val isRootStructType = concept.type is StructType
        if (concept.properties != null) {
            file.addType(mapStructType(concept, isRootStructType))
        }
        if (!isRootStructType) {
            val resType = mapTypeDefinition(concept.type, concept, null, true)
            if (resType.declaration == null) {
                val result = TypeAliasSpec.builder(concept.name, resType.noDec()).apply {
                    addDescription(concept.description)
                }.build()
                file.addTypeAlias(result)
            }
        }
    }

    private val innerStringUnions = mutableMapOf<Set<String>, ClassName>()
    private val noSourcePrefix = setOf(
        "filter_mode",
        "gui_mode",
        "logistic_mode",
        "research_queue_setting"
    )

    private fun makeInnerStringUnion(
        source: ProtoOrConcept,
        property: Property,
        typeDefinition: UnionType,
        values: Set<String>
    ): ClassName = innerStringUnions.getOrPut(values) {
        val noPrefix = noSourcePrefix.any { property.name.endsWith(it) }
        val name = if (noPrefix) property.name.toCamelCase()
        else source.name + property.name.toCamelCase()
        val className = ClassName(packageName, name)
        val typeDescriptions = typeDefinition.options.associate {
            it as LiteralType
            it.value.content to it.description
        }
        val enumType = TypeSpec.enumBuilder(className).apply {
            for (value in values) {
                addEnumConstant(
                    value,
                    TypeSpec.anonymousClassBuilder().apply {
                        addDescription(typeDescriptions[value])
                    }.build()
                )
            }
            addAnnotation(Serializable::class)
        }.build()
        file.addType(enumType)
        className
    }

    private data class TypeDefinitionResult(
        val typeName: TypeName,
        val declaration: TypeSpec?
    ) {
        fun noDec(): TypeName {
            require(declaration == null) {
                "Declaration already set"
            }
            return typeName
        }
    }

    private fun TypeName.res(): TypeDefinitionResult = TypeDefinitionResult(this, null)
    private fun mapTypeDefinition(
        typeDefinition: TypeDefinition,
        source: ProtoOrConcept,
        property: Property?,
        isRoot: Boolean = false
    ): TypeDefinitionResult {

        return when (typeDefinition) {
            is BasicType -> {
                val value = typeDefinition.value
                builtins.getOrElse(value) { ClassName(packageName, value) }.res()
            }

            is ArrayType -> List::class.asClassName()
                .parameterizedBy(mapTypeDefinition(typeDefinition.value, source, property).typeName)
                .res()

            is DictType -> Map::class.asClassName().parameterizedBy(
                mapTypeDefinition(typeDefinition.key, source, property).noDec(),
                mapTypeDefinition(typeDefinition.value, source, property).noDec()
            ).res()

            is LiteralType -> {
                val v = typeDefinition.value
                when {
                    v.isString -> ClassName(packageName, "UnknownStringLiteral").res()
                    v.booleanOrNull != null -> ClassName(packageName, "UnknownBooleanLiteral").res()
                    v.intOrNull != null -> ClassName(packageName, "UnknownIntegerLiteral").res()
                    v.doubleOrNull != null -> ClassName(packageName, "UnknownDoubleLiteral").res()
                    else -> throw IllegalArgumentException("Unknown literal type: $v")
                }
            }

            StructType -> {
                require(source is Concept)
                ClassName(packageName, source.name + "Values").res()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val value0 = getReferencedType(typeDefinition.values[0])
                if (typeDefinition.values.all { getReferencedType(it) == value0 }) {
                    ClassName(packageName, "Tuple$numEls").parameterizedBy(
                        mapTypeDefinition(typeDefinition.values[0], source, property).noDec()
                    ).res()
                } else {
                    ClassName(packageName, "UnknownTuple").res()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, source, property)
            is UnionType -> {
                // check if is string union
                val isStringUnion = typeDefinition.options.all { it is LiteralType && it.value.isString }
                if (isStringUnion) {
                    return makeEnum(source, property, isRoot, typeDefinition)
                }
                return tryMakeItemOrList(source, property, typeDefinition) ?: ClassName(
                    packageName,
                    "UnknownUnion"
                ).res()
            }
        }
    }

    private fun tryMakeItemOrList(
        source: ProtoOrConcept,
        property: Property?,
        typeDefinition: UnionType
    ): TypeDefinitionResult? {
        if (typeDefinition.options.size != 2) return null
        val first = typeDefinition.options[0]
        val second = typeDefinition.options[1]
        if (second is ArrayType && second.value == first) {
            return ClassName(packageName, "ItemOrList").parameterizedBy(
                mapTypeDefinition(first, source, property).typeName // allow declaration here
            ).res()
        }
        if (second is TupleType && second.values.all { it == first }) {
            return ClassName(packageName, "ItemOrTuple${second.values.size}").parameterizedBy(
                mapTypeDefinition(first, source, property).noDec()
            ).res()
        }
        return null
    }

    private fun makeEnum(
        source: ProtoOrConcept,
        property: Property?,
        isRootConcept: Boolean,
        typeDefinition: UnionType
    ): TypeDefinitionResult {
        if (source is Concept && property == null) {
            val className = ClassName(
                packageName,
                if (isRootConcept) source.name else source.name + "Values"
            )
            val enumType = TypeSpec.enumBuilder(className).apply {
                for (option in typeDefinition.options) {
                    val value = (option as LiteralType).value.content
                    addEnumConstant(value)
                }
                addAnnotation(Serializable::class)
                addDescription(source.description)
            }.build()
            this.file.addType(enumType)
            return TypeDefinitionResult(className, enumType)
        } else {
            requireNotNull(property)
            val values = typeDefinition.options.map { (it as LiteralType).value.content }.toSet()
            val className = makeInnerStringUnion(source, property, typeDefinition, values)
            return TypeDefinitionResult(className, null)
        }
    }

    private fun mapProperty(from: ProtoOrConcept, property: Property): PropertySpec {
        require(property.visibility == null)
        val nullable = property.optional ||
                property.default != null ||
                childOverrides[from]?.get(property.name) == OverrideType.AsNullable
        val isUnknown = overriddes[from]?.get(property.name) == OverrideType.Unknown ||
                childOverrides[from]?.get(property.name) == OverrideType.Unknown
        val laterOverridden = childOverrides[from]?.contains(property.name) == true

        val type =
            (if (!isUnknown)
                mapTypeDefinition(property.type, from, property).noDec()
            else
                ClassName(packageName, "UnknownOverriddenType"))
                .copy(nullable = nullable)



        return PropertySpec.builder(property.name, type).apply {
            addDescription(property.description)
            if (property.override) addModifiers(KModifier.OVERRIDE)
            if (laterOverridden) addModifiers(KModifier.OPEN)
            val altName = property.alt_name
            if (altName != null) {
                delegate("fromJson(%S)", altName)
            } else {
                delegate("fromJson()")
            }
        }.build()
    }

    private fun mapStructType(
        concept: Concept,
        isRoot: Boolean
    ): TypeSpec {
        val name = if (isRoot) concept.name else concept.name + "Values"
        return TypeSpec.classBuilder(name).apply {
            commonSetup(concept)
            for (property in concept.properties!!.sortedBy { it.order }) {
                addProperty(mapProperty(concept, property))
            }
        }.build()
    }

    private fun generatePrototypeDataClass() {
        val result = TypeSpec.classBuilder("PrototypeData").apply {
            superclass(ClassName(packageName, "JsonReader"))
            addType(TypeSpec.companionObjectBuilder().apply {
                addSerializerMethod("PrototypeData")
            }.build())

            for (prototype in docs.prototypes.sortedBy { it.order }) {
                val typename = prototype.typename ?: continue
                // val `typename`: Map<String, Type> by fromJson()
                addProperty(
                    PropertySpec.builder(
                        typename, Map::class.asClassName().parameterizedBy(
                            String::class.asClassName(),
                            ClassName(packageName, prototype.name)
                        ).copy(nullable = true)
                    )
                        .delegate("fromJson()")
                        .build()
                )

            }


        }.build()
        file.addType(result)
    }

    companion object {
        val builtins = mapOf(
            "bool" to Boolean::class.asClassName(),
            "double" to Double::class.asClassName(),
            "float" to Float::class.asClassName(),
            "int8" to Byte::class.asClassName(),
            "int16" to Short::class.asClassName(),
            "int32" to Int::class.asClassName(),
            "string" to String::class.asClassName(),
            "uint8" to UByte::class.asClassName(),
            "uint16" to UShort::class.asClassName(),
            "uint32" to UInt::class.asClassName(),
            "uint64" to ULong::class.asClassName(),
            "Vector" to ClassName(packageName, "Vector"),
            "Vector3D" to ClassName(packageName, "Vector3D"),
            "BoundingBox" to ClassName(packageName, "BoundingBox"),
        )
        val toIgnore = setOf("DataExtendMethod", "Data")
    }

    private fun TypeDefinition.isNumeric(): Boolean {
        val referenced = getReferencedType(this)
        if (referenced != null) {
            return "int" in referenced || referenced == "float" || referenced == "double"
        }
        val innerType = innerType()
        if (innerType is LiteralType) {
            return !innerType.value.isString && innerType.value.doubleOrNull != null
        }
        return false
    }

    private fun TypeDefinition.isBoolish(): Boolean {
        val referenced = getReferencedType(this)
        if (referenced == "bool") return true
        val innerType = innerType()
        if (innerType is LiteralType) {
            return innerType.value.booleanOrNull != null
        }
        return false
    }

    private fun TypeDefinition.isString(): Boolean = this is BasicType && value == "string"
            || this is LiteralType && value.isString
}

private fun Documentable.Builder<*>.addDescription(description: String?) {
    if (!description.isNullOrEmpty()) addKdoc(description.replace("%", "%%"))
}

private fun String.toCamelCase(): String {
    return this.split("_")
        .joinToString("") { s -> s.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
}
