import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull

class DeclarationGenerator(private val docs: ApiDocs) {
    init {
        require(
            docs.application == "factorio" &&
                    docs.api_version == 5 &&
                    docs.stage == "prototype"
        )
    }

    //    val prototypesByName = docs.prototypes.associateBy { it.name }
//    val conceptsByName = docs.types.associateBy { it.name }
    private val byName = (docs.prototypes + docs.types).associateBy { it.name }

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

    private val childOverridesAsNullable = mutableMapOf<ProtoOrConcept, MutableSet<String>>()
    private val overrideIgnore = mutableMapOf<ProtoOrConcept, MutableSet<String>>()
    private val unknownOverrides = mutableMapOf<ProtoOrConcept, MutableSet<String>>()
    private fun findOverriddes() {
        tailrec fun findParentProp(
            prototype: ProtoOrConcept,
            propName: String
        ): Property {
            return prototype.properties!!.find { it.name == propName } ?: findParentProp(
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
                val parentProp = findParentProp(parent, prop.name)
                if (prop.type.typeEquals(parentProp.type)) {
                    if (prop.optional && !parentProp.optional) {
                        childOverridesAsNullable
                            .getOrPut(parent, ::mutableSetOf)
                            .add(prop.name)
                    } else if (prop.optional == parentProp.optional) {
                        if (overrideIgnore[parent]?.contains(prop.name) == true) {
                            overrideIgnore.getOrPut(prototype, ::mutableSetOf)
                                .add(prop.name)
                        }
                    }
                } else if (parentProp.type.isNumeric() && prop.type.isNumeric()
                    || parentProp.type.isBoolish() && prop.type.isBoolish()
                    || parentProp.type.isString() && prop.type.isString()
                ) {
                    overrideIgnore
                        .getOrPut(prototype, ::mutableSetOf)
                        .add(prop.name)
                } else {
                    if (
                        prototype.name == "TurretPrototype" &&
                        prop.name == "corpse"
                    ) {
                        // ignore manually
                        overrideIgnore
                            .getOrPut(prototype, ::mutableSetOf)
                            .add(prop.name)
                    } else {
                        unknownOverrides
                            .getOrPut(parent, ::mutableSetOf)
                            .add(prop.name)
                        unknownOverrides
                            .getOrPut(prototype, ::mutableSetOf)
                            .add(prop.name)
                    }
                }
            }
        }
        for (value in byName.values) {
            visit(value)
        }
    }


    private val file: FileSpec.Builder = FileSpec.builder("", "FactorioApi")

    fun generate(): FileSpec {
        findOverriddes()
        docs.prototypes
            .sortedBy { it.order }
            .forEach { generatePrototypeInterface(it) }
        docs.types
            .sortedBy { it.order }
            .forEach { generateTypeInterface(it) }
        return file.build()
    }

    private fun generatePrototypeInterface(prototype: Prototype) {
        require(prototype.visibility == null)
        val result = TypeSpec.interfaceBuilder(prototype.name).apply {
            if (prototype.parent != null) {
                addSuperinterface(ClassName("", prototype.parent))
            }
            for (property in prototype.properties.sortedBy { it.order }) {
                if (overrideIgnore[prototype]?.contains(property.name) == true) {
                    continue
                }
                addProperty(mapProperty(prototype, property))
            }
        }.build()
        file.addType(result)
    }

    private fun generateTypeInterface(concept: Concept) {
        if (concept.name in builtins || concept.name in toIgnore) return
        val isRootStructType = concept.type is StructType
        if (concept.properties != null) {
            val structType = mapStructType(concept, isRootStructType)
                .apply { addDescription(concept.description) }
                .build()
            file.addType(structType)
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
        values: Set<String>
    ): ClassName {
        return innerStringUnions.getOrPut(values) {
            val noPrefix = noSourcePrefix.any { property.name.endsWith(it) }
            val name = if (noPrefix) property.name.toCamelCase()
            else source.name + property.name.toCamelCase()
            val className = ClassName("", name)
            val enumType = TypeSpec.enumBuilder(className).apply {
                for (value in values) {
                    addEnumConstant(value)
                }
                addAnnotation(Serializable::class)
            }.build()
            this.file.addType(enumType)
            className
        }
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
                builtins.getOrElse(value) { ClassName("", value) }.res()
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
                    v.isString -> ClassName("", "UnknownStringLiteral").res()
                    v.booleanOrNull != null -> ClassName("", "UnknownBooleanLiteral").res()
                    v.intOrNull != null -> ClassName("", "UnknownIntegerLiteral").res()
                    v.doubleOrNull != null -> ClassName("", "UnknownDoubleLiteral").res()
                    else -> throw IllegalArgumentException("Unknown literal type: $v")
                }
            }

            StructType -> {
                require(source is Concept)
                ClassName("", source.name + "Values").res()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val value0 = getReferencedType(typeDefinition.values[0])
                if (typeDefinition.values.all { getReferencedType(it) == value0 }) {
                    ClassName("", "Tuple$numEls").parameterizedBy(
                        mapTypeDefinition(typeDefinition.values[0], source, property).noDec()
                    ).res()
                } else {
                    ClassName("", "UnknownTuple").res()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, source, property)
            is UnionType -> {
                // check if is string union
                val isStringUnion = typeDefinition.options.all { it is LiteralType && it.value.isString }
                if (isStringUnion) {
                    return makeEnum(source, property, isRoot, typeDefinition)
                }
                return tryMakeItemOrList(source, property, typeDefinition) ?: ClassName("", "UnknownUnion").res()
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
            return ClassName("", "ItemOrList").parameterizedBy(
                mapTypeDefinition(first, source, property).typeName // allow declaration here
            ).res()
        }
        if (second is TupleType && second.values.all { it == first }) {
            return ClassName("", "ItemOrTuple${second.values.size}").parameterizedBy(
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
                "",
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
            val className = makeInnerStringUnion(source, property, values)
            return TypeDefinitionResult(className, null)
        }
    }

    private fun mapProperty(from: ProtoOrConcept, property: Property): PropertySpec {
        require(property.visibility == null)
        val nullable = property.optional || childOverridesAsNullable[from]?.contains(property.name) == true
        val isUnknown = unknownOverrides[from]?.contains(property.name) == true
        val type =
            (if (!isUnknown)
                mapTypeDefinition(property.type, from, property).noDec()
            else
                ClassName("", "UnknownOverriddenType"))
                .copy(nullable = nullable)
        return PropertySpec.builder(property.name, type).apply {
            addDescription(property.description)
            if (property.override) addModifiers(KModifier.OVERRIDE)
        }.build()
    }

    private fun mapStructType(
        concept: Concept,
        isRoot: Boolean
    ): TypeSpec.Builder {
        val name = if (isRoot) concept.name else concept.name + "Values"
        return TypeSpec.interfaceBuilder(name).apply {
            for (property in concept.properties!!.sortedBy { it.order }) {
                addProperty(mapProperty(concept, property))
            }
            if (concept.parent != null) {
                addSuperinterface(ClassName("", concept.parent))
            }
        }
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
            "Vector" to ClassName("", "Vector"),
            "Vector3D" to ClassName("", "Vector3D"),
            "BoundingBox" to ClassName("", "BoundingBox"),
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

private fun Documentable.Builder<*>.addDescription(description: String) {
    if (description.isNotEmpty()) addKdoc(description.replace("%", "%%"))
}

private fun String.toCamelCase(): String {
    return this.split("_").joinToString("") { it.capitalize() }
}
