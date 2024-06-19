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
    val byName = (docs.prototypes + docs.types).associateBy { it.name }

    tailrec fun getReferencedType(type: TypeDefinition): String? {
        val type = type.innerType()
        if (type is BasicType) {
            val value = type.value
            if (value in builtins) return value
            val concept = byName[value]
            if (concept is Concept) {
                return getReferencedType(concept.type)
            }
        }
        return null
    }

    val childOverridesAsNullable = mutableMapOf<String, MutableSet<String>>()
    val overrideIgnore = mutableMapOf<String, MutableSet<String>>()
    val unknownOverrides = mutableMapOf<String, MutableSet<String>>()
    fun findOverriddes() {
        tailrec fun findParentProp(
            protoName: String,
            propName: String
        ): Property {
            val prototype = byName[protoName]!!
            return prototype.properties!!.find { it.name == propName } ?: findParentProp(
                prototype.parent ?: error("No parent for $protoName"),
                propName
            )
        }

        val visited = mutableSetOf<String>()
        fun visit(prototype: ProtoOrConcept) {
            if (prototype.name in visited) return
            visited.add(prototype.name)

            val parent = prototype.parent ?: return
            visit(byName[parent]!!)
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
                            overrideIgnore.getOrPut(prototype.name, ::mutableSetOf)
                                .add(prop.name)
                        }
                    }
                } else if (parentProp.type.isNumerical() && prop.type.isNumerical()
                    || parentProp.type.isBoolish() && prop.type.isBoolish()
                ) {
                    overrideIgnore
                        .getOrPut(prototype.name, ::mutableSetOf)
                        .add(prop.name)
                } else {
                    unknownOverrides
                        .getOrPut(parent, ::mutableSetOf)
                        .add(prop.name)
                    unknownOverrides
                        .getOrPut(prototype.name, ::mutableSetOf)
                        .add(prop.name)
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
                if (overrideIgnore[prototype.name]?.contains(property.name) == true) {
                    continue
                }
                addProperty(mapProperty(prototype.name, property))
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
            val resType = mapTypeDefinition(concept.type, concept, true)
            if (resType.declaration == null) {
                val result = TypeAliasSpec.builder(concept.name, resType.noDec()).apply {
                    addDescription(concept.description)
                }.build()
                file.addTypeAlias(result)
            }
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

    private fun mapTypeDefinition(
        typeDefinition: TypeDefinition,
        conceptName: Concept?,
        isRoot: Boolean = false
    ): TypeDefinitionResult {
        fun TypeName.res(): TypeDefinitionResult = TypeDefinitionResult(this, null)

        return when (typeDefinition) {
            is BasicType -> {
                val value = typeDefinition.value
                builtins.getOrElse(value) { ClassName("", value) }.res()
            }

            is ArrayType -> List::class.asClassName()
                .parameterizedBy(mapTypeDefinition(typeDefinition.value, conceptName).typeName)
                .res()

            is DictType -> Map::class.asClassName().parameterizedBy(
                mapTypeDefinition(typeDefinition.key, conceptName).noDec(),
                mapTypeDefinition(typeDefinition.value, conceptName).noDec()
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
                require(conceptName != null) { "StructType must have a name" }
                ClassName("", conceptName.name + "Values").res()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val typeNames = typeDefinition.values.map { mapTypeDefinition(it, conceptName).noDec() }
                when (numEls) {
                    0 -> throw IllegalArgumentException("Empty tuple")
                    1 -> List::class.asClassName().parameterizedBy(typeNames[0]).res()
                    else -> ClassName("", "UnknownTuple").res()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, conceptName)
            is UnionType -> {
                // check if is string union
                val isStringUnion = typeDefinition.options.all { it is LiteralType && it.value.isString }
                if (!isStringUnion) {
                    return ClassName("", "UnknownUnion").res()
                }
                if (conceptName == null) {
                    return ClassName("", "InnerStringUnion").res()
                }
                val className = ClassName(
                    "",
                    if (isRoot) conceptName.name else conceptName.name + "Values"
                )
                val enumType = TypeSpec.enumBuilder(className).apply {
                    for (option in typeDefinition.options) {
                        val value = (option as LiteralType).value.content
                        addEnumConstant(value)
                    }
                    addAnnotation(Serializable::class)
                    addDescription(conceptName.description)
                }.build()
                this.file.addType(enumType)
                return TypeDefinitionResult(className, enumType)
            }
        }
    }

    private fun mapProperty(from: String, property: Property): PropertySpec {
        require(property.visibility == null)
        val nullable = property.optional || childOverridesAsNullable[from]?.contains(property.name) == true
        val isUnknown = unknownOverrides[from]?.contains(property.name) == true
        val type =
            (if (!isUnknown)
                mapTypeDefinition(property.type, null).noDec()
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
                addProperty(mapProperty(concept.name, property))
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

    fun TypeDefinition.isNumerical(): Boolean {
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

    fun TypeDefinition.isBoolish(): Boolean {
        val referenced = getReferencedType(this)
        if (referenced == "bool") return true
        val innerType = innerType()
        if (innerType is LiteralType) {
            return innerType.value.booleanOrNull != null
        }
        return false
    }
}

private fun Documentable.Builder<*>.addDescription(description: String) {
    if (description.isNotEmpty()) addKdoc(description.replace("%", "%%"))
}
