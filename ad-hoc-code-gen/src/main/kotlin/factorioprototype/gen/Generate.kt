package factorioprototype.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

public const val packageName = "factorioprototype"

private val manuallySerialized = setOf(
    "ItemIngredientPrototype",
    "IngredientPrototype",
    "ItemProductPrototype",
    "ProductPrototype",
    "SpawnPoint"
)

private val flattenStructType = setOf(
    "ItemIngredientPrototype",
    "ItemProductPrototype",
    "SpawnPoint"
)

private val replaceType = mapOf("MapPosition" to BasicType("Vector"))

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
        file.addImport("kotlin.reflect", "typeOf")
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

    private sealed interface GeneratedType {
        val className: ClassName
    }

    private sealed interface ProtoOrConceptGen : UnionMember, GeneratedType {
        val name: String
        val inner: ProtoOrConcept
        override val className get() = ClassName(packageName, name)
        override fun getClassName(parent: Union): ClassName = className // ignore parent, as is not a union member
    }

    data class MainType(override val inner: ProtoOrConcept) : ProtoOrConceptGen {
        override val name: String get() = inner.name
    }

    data class ConceptValues(override val inner: Concept) : ProtoOrConceptGen {
        override val name: String get() = inner.name + "Values"
    }

    data object OtherGeneratedType : ProtoOrConceptGen {
        override val name: String get() = throw UnsupportedOperationException()
        override val inner: ProtoOrConcept get() = throw UnsupportedOperationException()
    }

    private sealed class UnionSubtype : UnionMember {
        abstract val subclassName: String
        override fun getClassName(parent: Union): ClassName = parent.className.nestedClass(subclassName)
        data class Primitive(val typeName: ClassName) : UnionSubtype() {
            override val subclassName: String get() = typeName.simpleName
        }

        data class Literal(val values: MutableList<LiteralType>) : UnionSubtype() {
            override val subclassName: String = values.first().value.content
                .replace(':', '_')
        }

        data class Array(val subType: TypeDefinition) : UnionSubtype() {
            override val subclassName: String get() = "Array"
        }
    }

    private sealed interface UnionMember {
        fun getClassName(parent: Union): ClassName
    }

    private sealed interface Union {
        val name: String
        val className: ClassName get() = ClassName(packageName, name)
        val members: Set<UnionMember>
    }

    private data class ConceptUnion(
        val concept: Concept,
        override val members: Set<UnionMember>
    ) : Union {
        init {
            require(concept.type.innerType() is UnionType)
        }

        override val name get() = concept.name
    }

    private data class InnerUnion(
        override val name: String,
        override val members: Set<UnionMember>
    ) : Union {
        var generated = false
    }

    private val conceptUnions = mutableMapOf<Concept, ConceptUnion>()

    private val unionSupertypes = mutableMapOf<GeneratedType, MutableSet<Union>>()

    private fun registerUnion(union: Union) {
        for (type in union.members) {
            if (type is GeneratedType) {
                unionSupertypes.getOrPut(type, ::mutableSetOf).add(union)
            }
        }
    }

    private val conceptDiscriminatorValue = mutableMapOf<Concept, String>()
    private fun getDiscriminatorValue(value: ProtoOrConcept): String? = when (value) {
        is Concept -> conceptDiscriminatorValue[value]
        is Prototype -> value.typename
    }

    private val conceptCanBeAutomaticUnionMember = mutableSetOf<Concept>()
    private fun UnionMember.canBeAutomaticUnionMember(): Boolean = when (this) {
        is ProtoOrConceptGen -> when (inner) {
            is Prototype -> true
            is Concept -> inner in conceptCanBeAutomaticUnionMember
        }

        is UnionSubtype -> false
    }

    private fun Union.canBeAutomaticallySerialized(): Boolean = members.all { it.canBeAutomaticUnionMember() }


    private fun getDiscriminatorPropName(concept: Concept): String {
        if (concept.name.startsWith("NoiseFunction")) {
            return "function_name"
        }
        return "type"
    }

    private tailrec fun TypeDefinition.followTypeAliases(): TypeDefinition {
        val innerType = innerType()
        if (innerType !is BasicType || innerType.value in predefined) return innerType
        val referenced = byName[innerType.value]
        if (referenced !is Concept) return innerType
        return (referenced.type).followTypeAliases()
    }

    private fun tryGetUnionMembers(sourceConcept: Concept?, type: TypeDefinition): Set<UnionMember>? {
        val innerType = type.innerType()
        if (innerType !is UnionType) return null
        val els = buildList {
            for (mRawType in innerType.options) {
                val memberType = mRawType.innerType()
                if (memberType is StructType && sourceConcept != null) {
                    add(ConceptValues(sourceConcept))
                } else if (memberType is BasicType) {
                    if (memberType.value in predefined) {
                        add(UnionSubtype.Primitive(predefined[memberType.value]!!))
                        continue
                    }
                    val referencedValue = byName[memberType.value] ?: run {
                        return null
                    }
                    if (referencedValue is Concept) {
                        val actualType = referencedValue.type.followTypeAliases()
                        if (actualType is BasicType && actualType.value in predefined) {
                            add(UnionSubtype.Primitive(MainType(referencedValue).className))// use name of concept, but it's a primitive
                            continue
                        }
                    }

                    add(MainType(referencedValue))
                } else if (memberType is LiteralType) {
                    val matching = this@buildList.find { m: UnionMember ->
                        m is UnionSubtype.Literal && m.values.first().value.content == memberType.value.content
                    }
                    if (matching != null) {
                        (matching as UnionSubtype.Literal).values.add(memberType)
                    } else {
                        add(UnionSubtype.Literal(mutableListOf(memberType)))
                    }
                } else if (memberType is ArrayType) {
                    if (this@buildList.any { it: UnionMember -> it is UnionSubtype.Array }) return null
                    add(UnionSubtype.Array(memberType.value))
                } else {
                    return null
                }
            }
        }
        if (els.all { it is UnionSubtype.Literal && it.values.all { value -> value.isString() } }) return null
        return els.toSet()
    }

    private fun findUnionTypes() {
        for (concept in typePartialOrder) {
            if (concept !is Concept || concept.name in toIgnore || concept.name in replaceType || concept.name in predefined) continue
            val innerType = concept.type.innerType()
            when {
                innerType is StructType || concept.name in flattenStructType -> {
                    val properties = concept.properties ?: continue
                    val discriminatorField = getDiscriminatorPropName(concept)
                    val discriminatorValue = properties.find { it.name == discriminatorField }?.type?.innerType()
                    if (discriminatorValue is LiteralType && discriminatorValue.value.isString) {
                        this.conceptDiscriminatorValue[concept] = discriminatorValue.value.content
                        conceptCanBeAutomaticUnionMember.add(concept)
                    }
                }

                innerType is BasicType -> {
                    val referenced = byName[innerType.value] ?: continue
                    if (referenced in conceptCanBeAutomaticUnionMember) {
                        conceptCanBeAutomaticUnionMember.add(concept)
                    }
                }

                innerType is UnionType -> {
                    val unionMembers = tryGetUnionMembers(
                        concept,
                        innerType
                    ) ?: continue
                    val union = ConceptUnion(concept, unionMembers)
                    if (union.canBeAutomaticallySerialized())
                        conceptCanBeAutomaticUnionMember.add(concept)
                    conceptUnions[concept] = union
                    registerUnion(union)
                }

                else -> {}
            }
        }
    }


    private val innerUnions = mutableMapOf<Set<UnionMember>, InnerUnion>()
    private fun findInnerUnions() {
        fun tryGetNameByLetterPrefix(options: Set<UnionMember>): String? {
            if (!options.all { it is ProtoOrConceptGen }) return null
            val optionNames = options.map { (it as ProtoOrConceptGen).name }
            val baseName = optionNames.reduce(String::commonSuffixWith).let {
                it.substring(it.indexOfFirst { c -> c.isUpperCase() })
            }
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

    private fun TypeSpec.Builder.setupClass(value: ProtoOrConceptGen) {
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
        for (superType in unionSupertypes[(value)] ?: emptyList()) {
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
        addSubclassSerializer(ClassName(packageName, className), "JsonReaderSerializer")
    }

    private fun TypeSpec.Builder.addSubclassSerializer(
        className: ClassName,
        serializerType: String,
        buildSerializerObject: TypeSpec.Builder.() -> Unit = {}
    ) {
        addAnnotation(
            AnnotationSpec.builder(Serializable::class).addMember(
                "%T::class",
                className.nestedClass("Serializer")
            ).build()
        )
        addType(TypeSpec.objectBuilder("Serializer").apply {
            superclass(
                ClassName(packageName, serializerType).parameterizedBy(className)
            )
            addSuperclassConstructorParameter("%T::class", className)
            this.buildSerializerObject()
        }.build())
    }

    private fun generateClass(
        type: ProtoOrConceptGen,
    ) {
        val result = TypeSpec.classBuilder(type.className).apply { setupClass(type) }.build()
        file.addType(result)
    }

    private fun generatePrototype(prototype: Prototype) {
        generateClass(MainType(prototype))
    }

    private fun generateConcept(concept: Concept) {
        if (concept.name in predefined || concept.name in toIgnore) return
        val type = replaceType[concept.name] ?: concept.type
        val isSimpleStructType = type is StructType || concept.name in flattenStructType
        if (concept.properties != null && concept.name !in replaceType) {
            val genType = if (isSimpleStructType) MainType(concept) else ConceptValues(concept)
            generateClass(genType)
        }
        if (!isSimpleStructType) {
            val mainType = mapTypeDefinition(type, MainType(concept), null, isRoot = true)
            if (mainType.declaration == null) {
                val result = TypeAliasSpec.builder(concept.name, mainType.assertNoDec()).apply {
                    addDescription(concept.description)
                }.build()
                file.addTypeAlias(result)
            }
        }
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
        source: ProtoOrConceptGen,
        property: Property?,
        isRoot: Boolean = false
    ): TypeNameWithDecl {
        return when (typeDefinition) {
            is BasicType -> {
                val value = typeDefinition.value
                predefined.getOrElse(value) { ClassName(packageName, value) }.withNoDec()
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
                val value0 = typeDefinition.values[0].followTypeAliases()
                if (typeDefinition.values.all { it.followTypeAliases() == value0 }) {
                    ClassName(packageName, "Tuple$numEls").parameterizedBy(
                        mapTypeDefinition(typeDefinition.values[0], source, property).assertNoDec()
                    ).withNoDec()
                } else {
                    ClassName(packageName, "UnknownTuple").withNoDec()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, source, property)
            is UnionType -> {
                tryMakeEnum(source, property, typeDefinition, isRoot)
                    ?: tryMakeAllOneTypeUnion(typeDefinition)
                    ?: tryGenerateUnion(source, property)
                    ?: tryMakeItemOrList(source, property, typeDefinition)
                    ?: ClassName(packageName, "UnknownUnion").withNoDec()
            }
        }
    }

    private fun tryMakeItemOrList(
        source: ProtoOrConceptGen,
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


    private fun tryGenerateUnion(source: ProtoOrConceptGen, property: Property?): TypeNameWithDecl? {
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

    private val noiseFunctionApplication = byName["NoiseFunctionApplication"] as Concept
    private fun generateUnion(union: Union): TypeNameWithDecl {
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
                    add(" - [%L]\n", member.getClassName(union).simpleNames.joinToString("."))
                }
            })
            if (union.name in manuallySerialized) {
                addManualSerializerAnnotation(union.className)
            } else if (union.canBeAutomaticallySerialized()) {
                addAnnotation(Serializable::class)
            } else {
                addSubclassSerializer(union.className, "FirstMatchingSerializer") {
                    for (member in union.members) {
                        when (member) {
                            is ProtoOrConceptGen ->
                                addSuperclassConstructorParameter("%T::class", member.className)

                            is UnionSubtype -> {
                                addSuperclassConstructorParameter(
                                    "%T::class",
                                    member.getClassName(union)
                                )
                            }
                        }
                    }
                }
            }

            for (member in union.members) addUnionMember(member, union, concept)

            if (concept == noiseFunctionApplication) {
                addAnnotation(
                    AnnotationSpec.builder(JsonClassDiscriminator::class)
                        .addMember("%S", "function_name")
                        .build()
                )
            }

            if (concept != null) {
                val superTypes = unionSupertypes[MainType(concept)]
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

    private fun TypeSpec.Builder.addUnionMember(
        member: UnionMember,
        union: Union,
        concept: Concept?
    ) {
        if (member !is UnionSubtype) return
        fun TypeSpec.Builder.addSuperInterfaces() {
            addSuperinterface(union.className)
        }

        val className = member.getClassName(union)
        when (member) {
            is UnionSubtype.Primitive -> {
                // @JvmInline value class PrimitiveName(val value: PrimitiveType) : UnionName
                addType(TypeSpec.classBuilder(className).apply {
                    addModifiers(KModifier.VALUE)
                    addAnnotation(JvmInline::class)
                    addAnnotation(Serializable::class)
                    addSuperInterfaces()
                    addProperty(
                        PropertySpec.builder("value", member.typeName)
                            .initializer("value").build()
                    )
                    primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("value", member.typeName)
                            .build()
                    )
                }.build())
            }

            is UnionSubtype.Literal -> {
                // data object LiteralName : LiteralValue(value), UnionName
                addType(TypeSpec.objectBuilder(className).apply {
                    addSubclassSerializer(className, "LiteralValueSerializer")
                    addModifiers(KModifier.DATA)
                    superclass(ClassName(packageName, "LiteralValue"))
                    addSuperInterfaces()
                    for (value in member.values) {
                        if (value.value.isString)
                            addSuperclassConstructorParameter(
                                "%T(%S)",
                                JsonPrimitive::class.asClassName(),
                                value.value.content
                            )
                        else
                            addSuperclassConstructorParameter(
                                "%T(%L)",
                                JsonPrimitive::class.asClassName(),
                                value.value.content
                            )
                    }
                }.build())
            }

            is UnionSubtype.Array -> {
                val source = if (concept != null) MainType(concept) else OtherGeneratedType
                val innerType = mapTypeDefinition(member.subType, source, null)
                // class Array(values: List<T>) : ArrayValues<T>(values)
                addType(TypeSpec.classBuilder(className).apply {
                    primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("values", List::class.asClassName().parameterizedBy(innerType.typeName))
                            .build()
                    )
                    superclass(
                        ClassName(packageName, "ArrayValue")
                            .parameterizedBy(innerType.typeName)
                    )
                    addSuperclassConstructorParameter("values")
                    addSuperInterfaces()

                    addSubclassSerializer(className, "ArrayValueSerializer") {
                        addSuperclassConstructorParameter("typeOf<%T>()", innerType.typeName)
                    }
                }.build())
            }
        }
    }

    private fun tryMakeEnum(
        source: ProtoOrConceptGen,
        property: Property?,
        typeDefinition: UnionType,
        isRoot: Boolean
    ): TypeNameWithDecl? {
        if (canBeEnum(typeDefinition)) {
            return makeEnum(source, property, typeDefinition, isRoot)
        }
        return null
    }

    private fun canBeEnum(typeDefinition: TypeDefinition) =
        typeDefinition is UnionType &&
                typeDefinition.options.all { it is LiteralType && it.value.isString }

    private val innerStringUnions = mutableMapOf<Set<String>, ClassName>()
    private val noSourcePrefix = setOf(
        "filter_mode",
        "gui_mode",
        "logistic_mode",
        "research_queue_setting"
    )

    private fun makeEnum(
        source: ProtoOrConceptGen,
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
        val superTypes: Set<Union>?
        if (property == null) {
            className = if (!isRoot) {
                ClassName(packageName, source.name + "Strings")
            } else {
                source.className
            }
            superTypes = unionSupertypes[source]
        } else {
            if (values in innerStringUnions) return TypeNameWithDecl(innerStringUnions[values]!!, null)

            val noPrefix = noSourcePrefix.any { property.name.endsWith(it) }
            val name = if (noPrefix) property.name.toCamelCase()
            else source.inner.name + property.name.toCamelCase()
            className = ClassName(packageName, name)
            superTypes = null
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
            for (superType in superTypes ?: emptyList()) {
                addSuperinterface(superType.className)
            }
        }.build()
        file.addType(enumType)
        if (property != null) {
            innerStringUnions.getOrPut(values) { className }
        }
        return TypeNameWithDecl(className, enumType)
    }

    private fun tryMakeAllOneTypeUnion(typeDefinition: UnionType): TypeNameWithDecl? {
        val options = typeDefinition.options.map { it.followTypeAliases() }
        if (options.all { it.isInt() }) {
            return Byte::class.asClassName().withNoDec()
        }
        if (options.all { it is BasicType && it.value == "string" }) {
            return String::class.asClassName().withNoDec()
        }
        return null
    }


    private fun mapProperty(source: ProtoOrConceptGen, property: Property): PropertySpec {
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
        private val builtins = mapOf(
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
        )
        val predefined = builtins + mapOf(
            "Vector" to ClassName(packageName, "Vector"),
            "Vector3D" to ClassName(packageName, "Vector3D"),
            "BoundingBox" to ClassName(packageName, "BoundingBox"),
            "Color" to ClassName(packageName, "Color"),
        )
        val toIgnore = setOf("DataExtendMethod", "Data")
    }

    private fun TypeDefinition.isNumeric(): Boolean = when (val referenced = followTypeAliases()) {
        is BasicType -> {
            val value = referenced.value
            "int" in value || value == "float" || value == "double"
        }

        is LiteralType -> !referenced.value.isString && referenced.value.doubleOrNull != null
        else -> false
    }


    private fun TypeDefinition.isInt(): Boolean = when (val referenced = followTypeAliases()) {
        is BasicType -> "int" in referenced.value
        is LiteralType -> !referenced.value.isString && referenced.value.longOrNull != null
        else -> false
    }


    private fun TypeDefinition.isBool(): Boolean = when (val referenced = followTypeAliases()) {
        is BasicType -> referenced.value == "bool"
        is LiteralType -> !referenced.value.isString && referenced.value.booleanOrNull != null
        else -> false
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
