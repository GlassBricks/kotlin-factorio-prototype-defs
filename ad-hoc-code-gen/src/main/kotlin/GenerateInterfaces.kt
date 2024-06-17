import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull


fun Documentable.Builder<*>.addDescription(description: String) {
    if (description.isNotEmpty()) addKdoc(description.replace("%", "%%"))
}

class InterfaceGenerator(private val docs: ApiDocs) {
    init {
        require(
            docs.application == "factorio" &&
                    docs.api_version == 5 &&
                    docs.stage == "prototype"
        )
    }


    private val builder: FileSpec.Builder = FileSpec.builder("", "FactorioApi")

    fun generate(): FileSpec {
        docs.prototypes
            .sortedBy { it.order }
            .forEach { generatePrototypeInterface(it) }
        docs.types
            .sortedBy { it.order }
            .forEach { generateTypeInterface(it) }
        return builder.build()
    }

    private fun generatePrototypeInterface(prototype: Prototype) {
        require(prototype.visibility == null)
        val result = TypeSpec.interfaceBuilder(prototype.name).apply {
            if (prototype.parent != null) {
                addSuperinterface(ClassName("", prototype.parent))
            }
            for (property in prototype.properties.sortedBy { it.order }) {
                addProperty(mapProperty(property))
            }
        }.build()
        builder.addType(result)
    }

    private fun generateTypeInterface(type: Type) {
        if (type.name in builtinMap || type.name in toIgnore) return
        val isRootStructType = type.type is StructType
        if (type.properties != null) {
            val structType = mapStructType(type, isRootStructType)
                .apply { addDescription(type.description) }
                .build()
            builder.addType(structType)
        }
        if (!isRootStructType) {
            val resType = mapTypeDefinition(type.type, type, true)
            if (resType.declaration == null) {
                val result = TypeAliasSpec.builder(type.name, resType.noDec()).apply {
                    addDescription(type.description)
                }.build()
                builder.addTypeAlias(result)
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
        typeName: Type?,
        isRoot: Boolean = false
    ): TypeDefinitionResult {
        fun TypeName.res(): TypeDefinitionResult = TypeDefinitionResult(this, null)

        return when (typeDefinition) {
            is BasicType -> {
                val value = typeDefinition.value
                builtinMap.getOrElse(value) { ClassName("", value) }.res()
            }

            is ArrayType -> List::class.asClassName()
                .parameterizedBy(mapTypeDefinition(typeDefinition.value, typeName).typeName)
                .res()

            is DictType -> Map::class.asClassName().parameterizedBy(
                mapTypeDefinition(typeDefinition.key, typeName).noDec(),
                mapTypeDefinition(typeDefinition.value, typeName).noDec()
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
                require(typeName != null) { "StructType must have a name" }
                ClassName("", typeName.name + "Values").res()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val typeNames = typeDefinition.values.map { mapTypeDefinition(it, typeName).noDec() }
                when (numEls) {
                    0 -> throw IllegalArgumentException("Empty tuple")
                    1 -> List::class.asClassName().parameterizedBy(typeNames[0]).res()
                    else -> ClassName("", "UnknownTuple").res()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, typeName)
            is UnionType -> {
                // check if is string union
                val isStringUnion = typeDefinition.options.all { it is LiteralType && it.value.isString }
                if (!isStringUnion) {
                    return ClassName("", "UnknownUnion").res()
                }
                if (typeName == null) {
                    return ClassName("", "InnerStringUnion").res()
                }
                val className = ClassName(
                    "",
                    if (isRoot) typeName.name else typeName.name + "Values"
                )
                val enumType = TypeSpec.enumBuilder(className).apply {
                    for (option in typeDefinition.options) {
                        val value = (option as LiteralType).value.content
                        addEnumConstant(value)
                    }
                    addAnnotation(Serializable::class)
                    addDescription(typeName.description)
                }.build()
                this.builder.addType(enumType)
                return TypeDefinitionResult(className, enumType)
            }
        }
    }

    private fun mapProperty(property: Property): PropertySpec {
        val type = mapTypeDefinition(property.type, null).noDec()
            .copy(nullable = property.optional)
        return PropertySpec.builder(property.name, type).apply {
            if (property.override) addModifiers(KModifier.OVERRIDE)
            addDescription(property.description)
        }.build()
    }

    private fun mapStructType(
        type: Type,
        isRoot: Boolean
    ): TypeSpec.Builder {
        val name = if (isRoot) type.name else type.name + "Values"
        return TypeSpec.interfaceBuilder(name).apply {
            for (property in type.properties!!.sortedBy { it.order }) {
                addProperty(mapProperty(property))
            }
            if (type.parent != null) {
                addSuperinterface(ClassName("", type.parent))
            }
        }
    }

}
