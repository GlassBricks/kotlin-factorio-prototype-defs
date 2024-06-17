import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull


class InterfaceGenerator(val docs: ApiDocs) {
    init {
        require(
            docs.application == "factorio" &&
                    docs.api_version == 5 &&
                    docs.stage == "prototype"
        )
    }

    val builder: FileSpec.Builder = FileSpec.builder("", "FactorioApi")

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
        val isRootStructType = type.type is StructType
        if (type.properties != null) {
            val name = if (isRootStructType) type.name else type.name + "Values"
            val structType = mapStructType(name, type.properties)
            builder.addType(structType)
        }
        if (!isRootStructType) {
            val resType = mapTypeDefinition(type.type, type.name)
            if (resType.declaration == null) {
                val result = TypeAliasSpec.builder(type.name, resType.noDec()).build()
                builder.addTypeAlias(result)
            }
        }
    }

    private data class TypeDefinitionResult(
        val type: TypeName,
        val declaration: TypeSpec?
    ) {
        fun noDec(): TypeName {
            require(declaration == null) { "Declaration already set" }
            return type
        }
    }

    private fun mapTypeDefinition(
        typeDefinition: TypeDefinition,
        rootTypeName: String?
    ): TypeDefinitionResult {
        fun TypeName.res(): TypeDefinitionResult = TypeDefinitionResult(this, null)

        return when (typeDefinition) {
            is BasicType -> ClassName("", typeDefinition.value).res()
            is ArrayType -> List::class.asClassName()
                .parameterizedBy(mapTypeDefinition(typeDefinition.value, rootTypeName).noDec())
                .res()

            is DictType -> Map::class.asClassName().parameterizedBy(
                mapTypeDefinition(typeDefinition.key, rootTypeName).noDec(),
                mapTypeDefinition(typeDefinition.value, rootTypeName).noDec()
            ).res()

            is LiteralType -> {
                val v = typeDefinition.value
                when {
                    v.isString -> String::class.asClassName()
                    v.booleanOrNull != null -> Boolean::class.asClassName()
                    v.intOrNull != null -> Int::class.asClassName()
                    v.doubleOrNull != null -> Double::class.asClassName()
                    else -> throw IllegalArgumentException("Unknown literal type: $v")
                }.res()
            }

            StructType -> {
                require(rootTypeName != null) { "StructType must have a name" }
                ClassName("", rootTypeName + "Values").res()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val typeNames = typeDefinition.values.map { mapTypeDefinition(it, rootTypeName).noDec() }
                when (numEls) {
                    0 -> Unit::class.asClassName()
                    1 -> typeNames[0]
                    2 -> Pair::class.asClassName().parameterizedBy(typeNames)
                    3 -> Triple::class.asClassName().parameterizedBy(typeNames)
                    else -> Any::class.asClassName()
                }.res()
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, rootTypeName)
            is UnionType -> {
                // check if is string union
                val isStringUnion = typeDefinition.options.all { it is LiteralType && it.value.isString }
                if (!isStringUnion || rootTypeName==null) {
                    return UnknownUnion::class.asClassName().res()
                }
                val builder = TypeSpec.enumBuilder(ClassName("", rootTypeName))
                for (option in typeDefinition.options) {
                    val value = (option as LiteralType).value.content
                    builder.addEnumConstant(value)
                }
                builder.addAnnotation(Serializable::class)
                val enumType = builder.build()
                this.builder.addType(enumType)
                ClassName("", rootTypeName).res()
            }
        }
    }

    fun mapProperty(property: Property): PropertySpec {
        val type = mapTypeDefinition(property.type, null).noDec()
            .copy(nullable = property.optional)
        return PropertySpec.builder(property.name, type).apply {
            if (property.override) addModifiers(KModifier.OVERRIDE)
        }.build()
    }

    fun mapStructType(
        name: String,
        properties: List<Property>
    ): TypeSpec {
        return TypeSpec.interfaceBuilder(name).apply {
            for (property in properties.sortedBy { it.order }) {
                addProperty(mapProperty(property))
            }
        }.build()
    }

}
