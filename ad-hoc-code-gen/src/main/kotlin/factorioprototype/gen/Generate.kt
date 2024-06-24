package factorioprototype.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull

public const val packageName = "factorioprototype"

private val manuallySerialized = setOf(
    "ItemIngredientPrototype",
    "IngredientPrototype",
    "ItemProductPrototype",
    "ProductPrototype",
    "StreamAttackParametersGunCenterShift",
)

private val flattenStructType = setOf(
    "ItemIngredientPrototype",
    "ItemProductPrototype",
)

@OptIn(ExperimentalSerializationApi::class)
class DeclarationsGenerator(private val docs: ApiDocs) {
    init {
        require(
            docs.application == "factorio" &&
                    docs.api_version == 5 &&
                    docs.stage == "prototype"
        )
    }

    private val byName = (docs.prototypes + docs.types).associateBy { it.name }
    private val file: FileSpec.Builder = FileSpec.builder(packageName, "FactorioPrototypes")


    fun generate(): FileSpec {
        setupFile()
        findOverrides()
        findUnionTypes()
        findInnerUnions()
        docs.prototypes
            .sortedBy { it.order }
            .forEach { generatePrototype(it) }
        docs.types
            .sortedBy { it.order }
            .forEach { generateConcept(it) }

        generatePrototypeDataClass()
        return file.build()
    }


    // does not follow links via properties
    private val typePartialOrder: List<ProtoOrConcept> = run {
        val visited = mutableSetOf<ProtoOrConcept>()
        val result = mutableListOf<ProtoOrConcept>()
        val o = object {
            fun visitValue(value: ProtoOrConcept) {
                if (!visited.add(value)) return
                if (value.parent != null) {
                    visitValue(byName[value.parent]!!)
                }
                if (value is Concept) {
                    visitType(value.type)
                }
                result.add(value)
            }

            fun visitType(type: TypeDefinition) {
                when (type) {
                    is ArrayType -> visitType(type.value)
                    is BasicType -> {
                        val concept = byName[type.value]
                        if (concept != null) {
                            visitValue(concept)
                        }
                    }

                    is DictType -> {
                        visitType(type.key)
                        visitType(type.value)
                    }

                    is TupleType -> type.values.forEach { visitType(it) }
                    is TypeType -> visitType(type.value)
                    is UnionType -> type.options.forEach { visitType(it) }
                    StructType, is LiteralType -> {}
                }
            }
        }
        docs.prototypes.forEach(o::visitValue)
        docs.types.forEach(o::visitValue)
        result
    }

    private fun setupFile() {

        file.addFileComment("Automatically generated file, do not edit")
        file.addKotlinDefaultImports()
        file.addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "OptIn"))
                .addMember("%T::class", ClassName("kotlinx.serialization", "ExperimentalSerializationApi"))
                .build()
        )
    }


    private enum class OverrideType {
        Ignore,
        Unknown,
        AsNullable,
        Allowed
    }

    private val overrides = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()
    private val childOverrides = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()
    private fun findOverrides() {
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
                    } else if (prop.optional == parentProp.optional && overrides[parent]?.get(prop.name) == OverrideType.Ignore) {
                        OverrideType.Ignore
                    } else {
                        OverrideType.Allowed
                    }
                } else if (parentProp.type.isNumeric() && prop.type.isNumeric()
                    || parentProp.type.isBool() && prop.type.isBool()
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
                overrides.getOrPut(prototype, ::mutableMapOf)[prop.name] = overrideType
                childOverrides.getOrPut(source, ::mutableMapOf)[prop.name] = overrideType
            }

        }
        for (value in byName.values) {
            visit(value)
        }
    }

    private sealed interface TypeOrValues {
        val name: String
        val inner: ProtoOrConcept
        val className get() = ClassName(packageName, name)
    }

    data class MainType(override val inner: ProtoOrConcept) : TypeOrValues {
        override val name: String get() = inner.name
    }

    data class ConceptValues(override val inner: Concept) : TypeOrValues {
        override val name: String get() = inner.name + "Values"
    }

    private fun TypeOrValues.isClass(): Boolean = when (this) {
        is ConceptValues -> true
        is MainType -> inner is Prototype || (inner as? Concept)?.type is StructType
    }


    private sealed interface GeneratedUnion {
        val name: String
        val className: ClassName get() = ClassName(packageName, name)
        val members: Set<TypeOrValues>
    }

    private data class ConceptUnion(
        val concept: Concept,
        override val members: Set<TypeOrValues>
    ) : GeneratedUnion {
        init {
            require(concept.type.innerType() is UnionType)
        }

        override val name get() = concept.name
    }

    private data class InnerUnion(
        override val name: String,
        override val members: Set<TypeOrValues>
    ) : GeneratedUnion {
        var generated = false
    }

    private val conceptUnions = mutableMapOf<Concept, ConceptUnion>()

    private val _unionSupertypes = mutableMapOf<TypeOrValues, MutableSet<GeneratedUnion>>()
    private fun registerUnion(union: GeneratedUnion) {
        for (type in union.members) {
            _unionSupertypes.getOrPut(type, ::mutableSetOf).add(union)
        }
    }

    private val conceptDiscriminatorValue = mutableMapOf<Concept, String>()
    private fun getDiscriminatorValue(value: ProtoOrConcept): String? = when (value) {
        is Concept -> conceptDiscriminatorValue[value]
        is Prototype -> value.typename
    }

    private val conceptCanBeUnionMember = mutableSetOf<Concept>()
    private fun canBeUnionMember(value: ProtoOrConcept): Boolean =
        value is Prototype || value is Concept && value in conceptCanBeUnionMember

    private fun getDiscriminatorPropName(concept: Concept): String {
        if (concept.name.startsWith("NoiseFunction")) {
            return "function_name"
        }
        return "type"
    }

    private fun tryGetUnionMembers(sourceConcept: Concept?, type: TypeDefinition): Set<TypeOrValues>? {
        val innerType = type.innerType()
        if (innerType !is UnionType) return null

        return buildSet {
            fun maybeInlineMembers(referencedValue: ProtoOrConcept): Boolean {
                if (referencedValue !is Concept) return false
                val conceptUnion = conceptUnions[referencedValue] ?: return false
                if (conceptUnion.members.all { member ->
                        member is ConceptValues ||
                                member is MainType &&
                                getDiscriminatorValue(member.inner) == null
                    }) {
                    addAll(conceptUnion.members)
                    return true
                }
                return false
            }
            for (member in innerType.options) {
                val inner = member.innerType()
                if (inner is StructType && sourceConcept != null) {
                    add(ConceptValues(sourceConcept))
                } else if (inner is BasicType) {
                    val referencedValue = byName[inner.value] ?: return null
                    if (!canBeUnionMember(referencedValue))
                        return null
                    if (!maybeInlineMembers(referencedValue))
                        add(MainType(referencedValue))
                } else {
                    return null
                }
            }
        }
    }

    private fun findUnionTypes() {
        for (concept in typePartialOrder) {
            if (concept !is Concept) continue
            val innerType = concept.type.innerType()
            when {
                innerType is StructType || concept.name in flattenStructType -> {
                    conceptCanBeUnionMember.add(concept)
                    val properties = concept.properties ?: continue
                    val discriminatorField = getDiscriminatorPropName(concept)
                    val discriminatorValue = properties.find { it.name == discriminatorField }?.type?.innerType()
                    if (discriminatorValue is LiteralType && discriminatorValue.value.isString) {
                        this.conceptDiscriminatorValue[concept] = discriminatorValue.value.content
                    }
                }

                innerType is BasicType -> {
                    val referenced = byName[innerType.value] ?: continue
                    if (canBeUnionMember(referenced)) {
                        conceptCanBeUnionMember.add(concept)
                    }
                }

                innerType is UnionType -> {
                    val unionMembers = tryGetUnionMembers(
                        concept,
                        innerType
                    ) ?: continue
                    conceptCanBeUnionMember.add(concept)
                    for (member in unionMembers) {
                        require(canBeUnionMember(member.inner)) {
                            "Union member $member cannot be a union member of $concept"
                        }
                    }
                    val union = ConceptUnion(concept, unionMembers)
                    conceptUnions[concept] = union
                    registerUnion(union)
                }

                else -> {}
            }
        }
    }


    private val innerUnions = mutableMapOf<Set<TypeOrValues>, InnerUnion>()
    private fun findInnerUnions() {
        fun tryGetNameByLetterPrefix(options: Set<TypeOrValues>): String? {
            val optionNames = options.map { it.name }
            val baseName = optionNames.reduce(String::commonSuffixWith)
            if (baseName.isEmpty()) return null
            val uniqueNames = optionNames.map { it.removeSuffix(baseName) }
            val letters = uniqueNames.map { it.firstOrNull() ?: return null }
                .toCharArray()
            require(letters.toSet().size == letters.size) {
                "Not unique first letters, please handle: $letters"
            }
            return letters.concatToString() + baseName
        }

        fun visitProperty(
            source: ProtoOrConcept,
            property: Property,
        ) {
            val type = property.type.innerType()
            val options = tryGetUnionMembers(null, type) ?: return

            if (options in innerUnions) return

            val name = tryGetNameByLetterPrefix(options)
                ?: (source.name + property.name.toCamelCase())

            val union = InnerUnion(name, options)
            registerUnion(union)
            innerUnions[options] = union
        }

        for (value in typePartialOrder) {
            val properties = value.properties ?: continue
            for (property in properties) {
                visitProperty(value, property)
            }
        }
    }

    private fun TypeSpec.Builder.setupClass(value: TypeOrValues) {
        if (value.inner.abstract) {
            addModifiers(KModifier.SEALED)
        } else {
            addModifiers(KModifier.OPEN)
        }
        val parent = value.inner.parent
        if (parent != null) {
            superclass(ClassName(packageName, parent))
        } else {
            superclass(ClassName(packageName, "JsonReader"))
        }
        for (superType in _unionSupertypes[(value)] ?: emptyList()) {
            addSuperinterface(superType.className)
        }
        addDescription(value.inner.description)
        if (!value.inner.abstract) {
            addJsonReaderSerializer(this@setupClass.build().name!!)
        }
        val discriminatorValue = getDiscriminatorValue(value.inner)
        if (discriminatorValue != null) {
            addAnnotation(
                AnnotationSpec.builder(SerialName::class)
                    .addMember("%S", discriminatorValue)
                    .build()
            )
        }

        for (property in value.inner.properties!!.sortedBy { it.order }) {
            if (overrides[value.inner]?.get(property.name) == OverrideType.Ignore) continue
            addProperty(mapProperty(value, property))
        }
    }

    private fun TypeSpec.Builder.addManualSerializerAnnotation(className: ClassName) {
        addAnnotation(
            AnnotationSpec.builder(Serializable::class)
                .addMember("%TSerializer::class", className)
                .build()
        )
    }

    private fun TypeSpec.Builder.addJsonReaderSerializer(className: String) {
        if (className in manuallySerialized) {
            addManualSerializerAnnotation(ClassName(packageName, className))
            return
        }
        addSubclassSerializer(className, "JsonReaderSerializer")
    }

    private fun TypeSpec.Builder.addSubclassSerializer(
        className: String,
        serializerType: String,
        buildSerializerObject: TypeSpec.Builder.() -> Unit = {}
    ) {
        addAnnotation(
            AnnotationSpec.builder(Serializable::class).addMember(
                "%T::class",
                ClassName(packageName, className, "Serializer")
            ).build()
        )
        addType(TypeSpec.objectBuilder("Serializer").apply {
            superclass(
                ClassName(packageName, serializerType).parameterizedBy(ClassName(packageName, className))
            )
            addSuperclassConstructorParameter("%T::class", ClassName(packageName, className))
            this.buildSerializerObject()
        }.build())
    }

    private fun generateClass(
        type: TypeOrValues,
    ) {
        val result = TypeSpec.classBuilder(type.className).apply { setupClass(type) }.build()
        file.addType(result)
    }

    private fun generatePrototype(prototype: Prototype) {
        generateClass(MainType(prototype))
    }

    private fun generateConcept(concept: Concept) {
        if (concept.name in builtins || concept.name in toIgnore) return
        val isSimpleStructType = concept.type is StructType || concept.name in flattenStructType
        if (concept.properties != null) {
            val type = if (isSimpleStructType) MainType(concept) else ConceptValues(concept)
            generateClass(type)
        }
        if (!isSimpleStructType) {
            val mainType = mapTypeDefinition(concept.type, MainType(concept), null, isRoot = true)
            if (mainType.declaration == null) {
                val result = TypeAliasSpec.builder(concept.name, mainType.assertNoDec()).apply {
                    addDescription(concept.description)
                }.build()
                file.addTypeAlias(result)
            }
        }
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

    private data class TypeNameWithDecl(
        val typeName: TypeName,
        val declaration: TypeSpec?
    ) {
        fun assertNoDec(): TypeName {
            require(declaration == null) {
                "Declaration already set"
            }
            return typeName
        }
    }

    private fun TypeName.withNoDec(): TypeNameWithDecl = TypeNameWithDecl(this, null)
    private fun mapTypeDefinition(
        typeDefinition: TypeDefinition,
        source: TypeOrValues,
        property: Property?,
        isRoot: Boolean = false
    ): TypeNameWithDecl {
        return when (typeDefinition) {
            is BasicType -> {
                val value = typeDefinition.value
                builtins.getOrElse(value) { ClassName(packageName, value) }.withNoDec()
            }

            is ArrayType -> List::class.asClassName()
                .parameterizedBy(mapTypeDefinition(typeDefinition.value, source, property).typeName)
                .withNoDec()

            is DictType -> Map::class.asClassName().parameterizedBy(
                mapTypeDefinition(typeDefinition.key, source, property).typeName,
                mapTypeDefinition(typeDefinition.value, source, property).assertNoDec()
            ).withNoDec()

            is LiteralType -> {
                val v = typeDefinition.value
                when {
                    v.isString -> ClassName(packageName, "UnknownStringLiteral").withNoDec()
                    v.booleanOrNull != null -> ClassName(packageName, "UnknownBooleanLiteral").withNoDec()
                    v.intOrNull != null -> ClassName(packageName, "UnknownIntegerLiteral").withNoDec()
                    v.doubleOrNull != null -> ClassName(packageName, "UnknownDoubleLiteral").withNoDec()
                    else -> throw IllegalArgumentException("Unknown literal type: $v")
                }
            }

            StructType -> {
                require(source is MainType && source.inner is Concept)
                return ConceptValues(source.inner as Concept).className.withNoDec()
            }

            is TupleType -> {
                val numEls = typeDefinition.values.size
                val value0 = getReferencedType(typeDefinition.values[0])
                if (typeDefinition.values.all { getReferencedType(it) == value0 }) {
                    ClassName(packageName, "Tuple$numEls").parameterizedBy(
                        mapTypeDefinition(typeDefinition.values[0], source, property).assertNoDec()
                    ).withNoDec()
                } else {
                    ClassName(packageName, "UnknownTuple").withNoDec()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, source, property)
            is UnionType -> {
                tryMakeStringUnion(source, property, typeDefinition, isRoot)
                    ?: tryMakeItemOrList(source, property, typeDefinition)
                    ?: tryGenerateUnion(source, property)
                    ?: ClassName(packageName, "UnknownUnion").withNoDec()
            }
        }
    }

    private fun tryMakeItemOrList(
        source: TypeOrValues,
        property: Property?,
        typeDefinition: UnionType
    ): TypeNameWithDecl? {
        if (typeDefinition.options.size != 2) return null
        val first = typeDefinition.options[0]
        val second = typeDefinition.options[1]
        val innerName = if (second is ArrayType && second.value == first) {
            "ItemOrList"
        } else if (second is TupleType && second.values.all { it == first }) {
            "ItemOrTuple${second.values.size}"
        } else {
            return null
        }

        return ClassName(packageName, innerName).parameterizedBy(
            mapTypeDefinition(first, source, property).typeName // allow declaration here
        ).withNoDec()
    }


    private fun tryGenerateUnion(source: TypeOrValues, property: Property?): TypeNameWithDecl? {
        if (property == null) {
            if (source is MainType && source.inner is Concept) {
                val union = conceptUnions[source.inner as Concept] ?: return null
                return generateUnion(union)
            }
        } else {
            return tryGetUnionMembers(null, property.type)
                ?.let { innerUnions[it]!! }
                ?.let { generateUnion(it) }
        }
        return null
    }

    private fun shouldUseFirstMatchingSubclassSerializer(union: GeneratedUnion): Boolean {
        return union.members.all {
            it.isClass() && getDiscriminatorValue(it.inner) == null
        }
    }

    private val noiseFunctionApplication = byName["NoiseFunctionApplication"] as Concept
    private fun generateUnion(union: GeneratedUnion): TypeNameWithDecl {
        if (union is InnerUnion) {
            if (union.generated) {
                return TypeNameWithDecl(union.className, null)
            }
            union.generated = true
        }
        val concept = if (union is ConceptUnion) union.concept else null
        val intf = TypeSpec.interfaceBuilder(union.className).apply {
            addModifiers(KModifier.SEALED)

            addDescription(concept?.description)
            addKdoc(buildCodeBlock {
                add("\n\nIncludes the following types:\n")
                for (member in union.members) {
                    add(" - [%L]\n", member.name)
                }
            })
            if (union.name in manuallySerialized) {
                addManualSerializerAnnotation(union.className)
            } else if (shouldUseFirstMatchingSubclassSerializer(union)) {
                addSubclassSerializer(union.name, "FirstMatchingSubclassSerializer") {
                    for (member in union.members) {
                        addSuperclassConstructorParameter("%T::class", member.className)
                    }
                }
            } else {
                addAnnotation(Serializable::class)
            }

            if (concept == noiseFunctionApplication) {
                addAnnotation(
                    AnnotationSpec.builder(JsonClassDiscriminator::class)
                        .addMember("%S", "function_name")
                        .build()
                )
            }

            if (concept != null) {
                val superTypes = _unionSupertypes[MainType(concept)]
                if (superTypes != null) {
                    for (superType in superTypes) {
                        if (superType != union) {
                            addSuperinterface(superType.className)
                        }
                    }
                }
            }
        }.build()
        file.addType(intf)
        return TypeNameWithDecl(union.className, intf)
    }

    private fun tryMakeStringUnion(
        source: TypeOrValues,
        property: Property?,
        typeDefinition: UnionType,
        isRoot: Boolean
    ): TypeNameWithDecl? =
        if (typeDefinition.options.all { it is LiteralType && it.value.isString }) {
            makeStringUnion(source, property, typeDefinition, isRoot)
        } else null

    private val innerStringUnions = mutableMapOf<Set<String>, ClassName>()
    private val noSourcePrefix = setOf(
        "filter_mode",
        "gui_mode",
        "logistic_mode",
        "research_queue_setting"
    )

    private fun makeStringUnion(
        source: TypeOrValues,
        property: Property?,
        typeDefinition: UnionType,
        isRoot: Boolean
    ): TypeNameWithDecl {
        val values = typeDefinition.options.map { (it as LiteralType).value.content }.toSet()
        val typeDescriptions = typeDefinition.options.associate {
            it as LiteralType
            it.value.content to it.description
        }
        val className: ClassName
        if (property == null) {
            className = if (!isRoot) {
                ClassName(packageName, source.name + "Strings")
            } else {
                source.className
            }
        } else {
            if (values in innerStringUnions) return TypeNameWithDecl(innerStringUnions[values]!!, null)

            val noPrefix = noSourcePrefix.any { property.name.endsWith(it) }
            val name = if (noPrefix) property.name.toCamelCase()
            else source.inner.name + property.name.toCamelCase()
            className = ClassName(packageName, name)
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
            if (property == null) {
                addDescription(source.inner.description)
            }
        }.build()
        file.addType(enumType)
        if (property != null) {
            innerStringUnions.getOrPut(values) { className }
        }
        return TypeNameWithDecl(className, enumType)
    }


    private fun mapProperty(source: TypeOrValues, property: Property): PropertySpec {
        require(property.visibility == null)
        val childOverrideType = childOverrides[source.inner]?.get(property.name)
        val nullable = property.optional ||
                property.default != null || childOverrideType == OverrideType.AsNullable
        val isUnknown = overrides[source.inner]?.get(property.name) == OverrideType.Unknown ||
                childOverrideType == OverrideType.Unknown
        val laterOverridden = childOverrides[source.inner]?.contains(property.name) == true

        val mainType = if (!isUnknown)
            mapTypeDefinition(property.type, source, property).typeName // allow declaration here
        else
            ClassName(packageName, "UnknownOverriddenType")
        val type = mainType.copy(nullable = nullable)


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

    private fun generatePrototypeDataClass() {
        val result = TypeSpec.classBuilder("PrototypeData").apply {
            superclass(ClassName(packageName, "JsonReader"))
            addJsonReaderSerializer("PrototypeData")

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
            "Color" to ClassName(packageName, "Color"),
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

    private fun TypeDefinition.isBool(): Boolean {
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
