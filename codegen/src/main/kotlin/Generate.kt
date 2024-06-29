import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

const val packageName = "glassbricks.factorio.prototypes"

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
        findWithShorthand()
        findUnionTypes()
        findPropertyInnerUnions()
        findOverrides()
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

//        @file:Suppress("PropertyName")
        file.addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "PropertyName")
                .addMember("%S", "EnumEntryName")
                .addMember("%S", "ClassName")
                .addMember("%S", "KDocUnresolvedReference")
                .build()
        )
    }

    private val withShorthandConcepts = mutableSetOf<Concept>()

    private fun findWithShorthand() {
        for (concept in docs.types) {
            if (concept.name in predefined) continue
            val type = concept.type.innerType()
            if (type is UnionType && type.options.size == 2 &&
                type.options[0] is StructType && type.options[1] is TupleType
            ) {
                withShorthandConcepts += concept
                println(concept.name)
            }
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
        override val members: Set<UnionMember>,
    ) : Union {
        init {
            require(concept.type.innerType() is UnionType)
        }

        override val name get() = concept.name
    }

    private data class InnerUnion(
        override val name: String,
        override val members: Set<UnionMember>,
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
        if (referenced !is Concept || referenced.type.innerType() !is BasicType) return innerType
        return referenced.type.followTypeAliases()
    }

    private fun tryGetUnionMembers(sourceConcept: Concept?, type: UnionType): Set<UnionMember>? {
        val els = buildList {
            for (mRawType in type.options) {
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
            val type = if (concept in withShorthandConcepts) StructType else concept.type
            when (val innerType = type.innerType()) {
                is StructType -> {
                    val properties = concept.properties ?: continue
                    val discriminatorField = getDiscriminatorPropName(concept)
                    val discriminatorValue = properties.find { it.name == discriminatorField }?.type?.innerType()
                    if (discriminatorValue is LiteralType && discriminatorValue.value.isString) {
                        this.conceptDiscriminatorValue[concept] = discriminatorValue.value.content
                        conceptCanBeAutomaticUnionMember.add(concept)
                    }
                }

                is BasicType -> {
                    val referenced = byName[innerType.value] ?: continue
                    if (referenced in conceptCanBeAutomaticUnionMember) {
                        conceptCanBeAutomaticUnionMember.add(concept)
                    }
                }

                is UnionType -> {
                    val itemOrList = tryGetItemOrList(innerType)?.first
                    if (itemOrList != null) {
                        tryAddInnerUnion(itemOrList, concept, "Values", false)
                        continue
                    }
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

                else -> {
                    tryAddInnerUnion(innerType, concept, "Values", false)
                }
            }
        }
    }


    private val innerUnions = mutableMapOf<Set<UnionMember>, InnerUnion>()

    private fun tryAddInnerUnion(
        type: TypeDefinition,
        source: ProtoOrConcept,
        subName: String,
        tryGetNameFromMembers: Boolean = true,
    ) {
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

        when (type) {
            is UnionType -> {
                val options = tryGetUnionMembers(null, type) ?: return
                if (options in innerUnions) return
                val name =
                    (if (tryGetNameFromMembers) tryGetNameByLetterPrefix(options) else null)
                        ?: (source.name + subName)

                val union = InnerUnion(name, options)
                registerUnion(union)
                innerUnions[options] = union
            }

            is DictType -> tryAddInnerUnion(type.value, source, subName, tryGetNameFromMembers)
            is TypeType -> tryAddInnerUnion(type.value, source, subName, tryGetNameFromMembers)
            is ArrayType -> tryAddInnerUnion(type.value, source, subName, tryGetNameFromMembers)
            is BasicType, is LiteralType, StructType, is TupleType -> {}
        }
    }

    private fun findPropertyInnerUnions() {

        for (value in typePartialOrder) {
            val properties = value.properties ?: continue
            for (property in properties) {
                val type = property.type.innerType()
                val itemOrList = tryGetItemOrList(type)?.first
                val actualType = itemOrList ?: type
                tryAddInnerUnion(actualType, value, property.name.toCamelCase())
            }
        }
    }


    private enum class OverrideType {
        Ignore,
        Allowed,
        Nullable,
        Unknown
    }

    private val overrides = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()
    private val childOverrides = mutableMapOf<ProtoOrConcept, MutableMap<String, OverrideType>>()

    private val hasChildren = mutableSetOf<ProtoOrConcept>()
    private fun findOverrides() {
        tailrec fun findParentProp(
            prototype: ProtoOrConcept,
            propName: String,
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
            hasChildren.add(parent)
            visit(parent)
            val properties = prototype.properties ?: return

            @Suppress("NAME_SHADOWING")
            fun isSubtype(
                parentType: TypeDefinition,
                childType: TypeDefinition,
            ): Boolean {
                val parentType = parentType.followTypeAliases()
                val childType = childType.followTypeAliases()
                if (parentType is UnionType) {
                    return parentType.options.any { it.typeEquals(childType) }
                }
                if (parentType is BasicType && childType is BasicType) {
                    val parentConcept = byName[parentType.value] as? Concept ?: return false
                    val childConcept = byName[childType.value] as? Concept ?: return false
                    if (unionSupertypes[MainType(childConcept)]?.any { it.name == parentConcept.name } == true)
                        return true
                    var cur = childConcept.parent
                    while (cur != null) {
                        if (cur == parentConcept.name) return true
                        cur = byName[cur]?.parent
                    }
                }
                return false
            }

            for (prop in properties.filter { it.override }) {
                val (source, parentProp) = findParentProp(parent, prop.name)
                val overrideType: OverrideType = if (prop.type.typeEquals(parentProp.type)) {
                    if (prop.optional && !parentProp.optional) {
                        OverrideType.Nullable
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
                    tryGetItemOrList(parentProp.type)?.first?.typeEquals(prop.type) == true
                ) {
                    OverrideType.Ignore
                } else if (
                    isSubtype(parentProp.type, prop.type)
                ) {
                    OverrideType.Allowed
                } else {
                    isSubtype(parentProp.type, prop.type)
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

    private fun TypeSpec.Builder.addJsonReaderSerializer(className: ClassName) {
        addSubclassSerializer(className, "JsonReaderSerializer")
    }

    private fun TypeSpec.Builder.addSubclassSerializer(
        className: ClassName,
        serializerType: String,
        buildSerializerObject: TypeSpec.Builder.() -> Unit = {},
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
        value: ProtoOrConceptGen,
    ) {
        val parent = value.inner.parent
        val useJsonReader =
            value.inner is Prototype || value.inner.abstract || value.inner in hasChildren || parent != null
        val isObject = !useJsonReader && value.inner.properties!!.singleOrNull()?.name == "type"
        val builder = if (isObject) {
            TypeSpec.objectBuilder(value.className)
        } else {
            TypeSpec.classBuilder(value.className)
        }

        val result = builder.apply {

            addDescription(value.inner.description)
            if (!value.inner.abstract && useJsonReader) {
                addJsonReaderSerializer(value.className)
            } else if (!useJsonReader) {
                addAnnotation(Serializable::class)
            }
            val discriminatorValue = getDiscriminatorValue(value.inner)
            if (discriminatorValue != null) {
                addAnnotation(
                    AnnotationSpec.builder(SerialName::class)
                        .addMember("%S", discriminatorValue)
                        .build()
                )
            }
            if (value.inner.abstract) {
                addModifiers(KModifier.ABSTRACT)
            } else if (value.inner in hasChildren) {
                addModifiers(KModifier.OPEN)
            } else if (!useJsonReader) {
                addModifiers(KModifier.DATA)
            }

            if (parent != null) {
                superclass(ClassName(packageName, parent))
            } else if (useJsonReader) {
                superclass(ClassName(packageName, "JsonReader"))
            }
            for (superType in unionSupertypes[(value)] ?: emptyList()) {
                addSuperinterface(superType.className)
            }

            if (isObject) {
                check(!useJsonReader)
                return@apply
            }

            val primaryConstructorBuilder = if (!useJsonReader) FunSpec.constructorBuilder() else null
            for (property in value.inner.properties!!.sortedBy { it.order }) {
                if (overrides[value.inner]?.get(property.name) == OverrideType.Ignore) continue
                if (discriminatorValue != null && property.name == "type") continue
                val (propertySpec, parameter) = generateProperty(value, property, useJsonReader)
                addProperty(propertySpec)
                primaryConstructorBuilder?.addParameter(parameter!!)
            }
            if (primaryConstructorBuilder != null) {
                primaryConstructor(primaryConstructorBuilder.build())
            }
        }.build()
        file.addType(result)
    }

    private fun generatePrototype(prototype: Prototype) {
        generateClass(MainType(prototype))
    }

    private fun generateConcept(concept: Concept) {
        if (concept.name in predefined || concept.name in toIgnore) return
        val type = replaceType[concept.name] ?: concept.type
        val isSimpleStructType = type is StructType || concept in withShorthandConcepts
        if (concept.properties != null && concept.name !in replaceType) {
            val genType = if (isSimpleStructType) MainType(concept) else ConceptValues(concept)
            generateClass(genType)
        }
        if (!isSimpleStructType || concept.name in replaceType) {
            val mainType = mapTypeDefinition(type, MainType(concept), null, isRoot = true)
            if (mainType.declaration == null) {
                val result = TypeAliasSpec.builder(concept.name, mainType.typeName).apply {
                    addDescription(concept.description)
                }.build()
                file.addTypeAlias(result)
            }
        }
    }

    private data class TypeNameWithDecl(
        val typeName: TypeName,
        val declaration: TypeSpec?,
    )

    private fun TypeName.withNoDec(): TypeNameWithDecl = TypeNameWithDecl(this, null)
    private fun mapTypeDefinition(
        typeDefinition: TypeDefinition,
        source: ProtoOrConceptGen,
        property: Property?,
        isRoot: Boolean = false,
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
                mapTypeDefinition(typeDefinition.value, source, property).typeName
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
                        mapTypeDefinition(typeDefinition.values[0], source, property).typeName
                    ).withNoDec()
                } else {
                    ClassName(packageName, "UnknownTuple").withNoDec()
                }
            }

            is TypeType -> mapTypeDefinition(typeDefinition.value, source, property)
            is UnionType -> {
                tryMakeEnum(source, property, typeDefinition, isRoot)
                    ?: tryMakeAllOneTypeUnion(typeDefinition)
                    ?: tryGenerateUnion(source, property, typeDefinition, isRoot)
                    ?: tryMakeItemOrList(source, property, typeDefinition)
                    ?: error("Unknown union type: $typeDefinition")
            }
        }
    }

    private fun tryGetItemOrList(typeDefinition: TypeDefinition): Pair<TypeDefinition, String>? {
        if (typeDefinition !is UnionType) return null
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
        return first to innerName
    }

    private fun tryMakeItemOrList(
        source: ProtoOrConceptGen,
        property: Property?,
        typeDefinition: UnionType,
    ): TypeNameWithDecl? {
        val (first, innerName) = tryGetItemOrList(typeDefinition) ?: return null
        return ClassName(packageName, innerName).parameterizedBy(
            mapTypeDefinition(first, source, property).typeName // allow declaration here
        ).withNoDec()
    }

    private fun tryGenerateUnion(
        source: ProtoOrConceptGen, property: Property?,
        typeDefinition: UnionType, isRoot: Boolean,
    ): TypeNameWithDecl? {
        if (property == null && isRoot) {
            if (source is MainType && source.inner is Concept) {
                val union = conceptUnions[source.inner as Concept] ?: return null
                return generateUnion(union)
            }
        } else {
            return tryGetUnionMembers(null, typeDefinition)
                ?.let { innerUnions[it] }
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
            if (kdoc.isNotEmpty()) {
                addKdoc("\n\n")
            }
            addKdoc(buildCodeBlock {
                add("Includes the following types:\n")
                for (member in union.members) {
                    add(" - [%L]\n", member.getClassName(union).simpleNames.joinToString("."))
                }
            })
            for (member in union.members) addUnionMember(member, union, concept)
            if (union.canBeAutomaticallySerialized()) {
                addAnnotation(Serializable::class)
            } else {
                addSubclassSerializer(union.className, "UnionSerializer") {
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
        concept: Concept?,
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
        isRoot: Boolean,
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
        isRoot: Boolean,
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
                val sourceName = source.name
                if (sourceName.endsWith("s"))
                    ClassName(packageName, sourceName.dropLast(1))
                else
                    ClassName(packageName, sourceName + "Strings")
            } else {
                source.className
            }
            superTypes = unionSupertypes[source]
        } else {
            if (values in innerStringUnions) return TypeNameWithDecl(innerStringUnions[values]!!, null)

            val sourceName = source.inner.name.let {
                // hardcoded for now
                if (it == "SignalColorMapping") "SignalIDConnector" else it
            }

            val noPrefix = noSourcePrefix.any { property.name.endsWith(it) }
            val name = if (noPrefix) property.name.toCamelCase()
            else sourceName + property.name.toCamelCase()
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


    private fun generateProperty(
        source: ProtoOrConceptGen, property: Property,
        useJsonReader: Boolean,
    ): Pair<PropertySpec, ParameterSpec?> {
        require(property.visibility == null)
        val childOverrideType = childOverrides[source.inner]?.get(property.name)
        val nullable = property.optional ||
                property.default != null || childOverrideType == OverrideType.Nullable
        val isUnknown = overrides[source.inner]?.get(property.name) == OverrideType.Unknown ||
                childOverrideType == OverrideType.Unknown
        val laterOverridden = childOverrides[source.inner]?.contains(property.name) == true

        val mainType = if (isUnknown) {
            ClassName(packageName, "UnknownOverriddenType")
        } else if (source is ConceptValues && source.inner.name == "Sound" && property.name == "variations") {
            val type = property.type as ArrayType
            val inner = mapTypeDefinition(type.value, source, property).typeName
            ClassName(packageName, "ItemOrList").parameterizedBy(inner)
        } else {
            mapTypeDefinition(property.type, source, property).typeName // allow declaration here
        }
        val type = mainType.copy(nullable = nullable)


        val propertySpec = PropertySpec.builder(property.name, type).apply {
            addDescription(property.description)
            if (property.override) addModifiers(KModifier.OVERRIDE)
            else if (laterOverridden) addModifiers(KModifier.OPEN)
            val altName = property.alt_name
            if (useJsonReader) {
                if (altName != null) {
                    delegate("fromJson(%S)", altName)
                } else {
                    delegate("fromJson()")
                }
            } else {
                initializer(property.name)
                if (altName != null) {
                    addAnnotation(AnnotationSpec.builder(JsonNames::class).addMember("%S", altName).build())
                }
            }
        }.build()

        val parameter: ParameterSpec? = if (useJsonReader) {
            null
        } else ParameterSpec.builder(propertySpec.name, type).apply {
            if (nullable) defaultValue("null")
        }.build()

        return propertySpec to parameter
    }

    private fun generatePrototypeDataClass() {
        val result = TypeSpec.classBuilder("PrototypeData").apply {
            superclass(ClassName(packageName, "PrototypeDataBase"))
            primaryConstructor(FunSpec.constructorBuilder()
                .addParameter("json", JsonObject::class).build())
            addSuperclassConstructorParameter("json")

            for (prototype in docs.prototypes.sortedBy { it.order }) {
                val typename = prototype.typename ?: continue
                addProperty(
                    PropertySpec.builder(
                        typename, Map::class.asClassName().parameterizedBy(
                            String::class.asClassName(),
                            ClassName(packageName, prototype.name)
                        )
                    )
                        .delegate("this")
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
