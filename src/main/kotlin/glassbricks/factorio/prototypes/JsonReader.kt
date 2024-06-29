package glassbricks.factorio.prototypes

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlin.math.roundToLong
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor


internal var eagerInit = false

public abstract class JsonReader {
    private lateinit var jsonObj: JsonObject

    private val values = mutableMapOf<String, Any?>()

    internal class Uninitialized(
        val altName: String?,
        val type: KType
    )

    @Suppress("UNCHECKED_CAST")
    internal fun getDeclaredKeys(): Map<String, Uninitialized> = values as Map<String, Uninitialized>

    public operator fun get(name: String): Any? {
        require(name in values) { "Unknown field: $name" }
        val value = values[name]
        if (value !is Uninitialized) return value
        return readValue(name, value.type, value.altName).also { values[name] = it }
    }

    public operator fun contains(key: String): Boolean = key in values
    
    public val keys: Set<String> get() = values.keys

    private fun readValue(
        name: String,
        type: KType,
        altName: String?
    ): Any? {
        val serializer = serializer(type)
        val el = jsonObj[name] ?: altName?.let { jsonObj[it] }
        if (el == null) {
            if (!type.isMarkedNullable) {
                val msg = "Missing required field: ${this::class.simpleName}.$name"
                if (!eagerInit) {
                    throw SerializationException(msg)
                } else {
                    //                        println(msg)
                }
            }
            return null
        }

        tryManuallyDeserializing(factorioPrototypeJson, el, serializer)?.let { return it }

        return factorioPrototypeJson.decodeFromJsonElement(serializer, el)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> fromJson(altName: String? = null): PropertyDelegateProvider<Any, ReadOnlyProperty<Any, T>> =
        PropertyDelegateProvider { _, property ->
            val type = property.returnType
            val name = property.name
            values[name] = Uninitialized(altName, type)
            ReadOnlyProperty { _, _ -> get(name) as T }
        }

    internal fun init(jsonObj: JsonObject, eager: Boolean = eagerInit) {
        this.jsonObj = jsonObj
        if (eager) {
            values.keys.forEach { get(it) }
        }
    }
    protected fun fakeInit(jsonObj: JsonObject) {
        this.jsonObj = jsonObj
    }

    override fun toString(): String {
        val simpleName = this::class.simpleName ?: super.toString()
        if ("name" in values) return "$simpleName(\"${get("name")}\")"
        return simpleName
    }
}


private val intDescriptors = arrayOf(
    Byte.serializer(),
    Short.serializer(),
    Int.serializer(),
    Long.serializer(),
    UByte.serializer(),
    UShort.serializer(),
    UInt.serializer(),
    ULong.serializer()
).map { it.descriptor }.toSet()

private val floatDescriptors = arrayOf(
    Float.serializer().descriptor,
    Double.serializer().descriptor
)

private val inlineTypes = setOf(
    Byte::class,
    Short::class,
    Int::class,
    Long::class,
    UByte::class,
    UShort::class,
    UInt::class,
    ULong::class,
    Float::class,
    Double::class,
    String::class,
    Boolean::class,
    Vector::class
)

@OptIn(ExperimentalSerializationApi::class)
private fun tryManuallyDeserializing(
    json: Json,
    el: JsonElement,
    serializer: KSerializer<*>
): Any? {
    val descriptor = serializer.descriptor.nonNullOriginal
    return when {
        descriptor.kind == StructureKind.LIST && el is JsonObject && (el.isEmpty() || "1" in el || "2" in el) -> {
            val list = buildList {
                var i = 1
                while (true) {
                    val element = el[i.toString()]
                    if (element != null) this.add(element)
                    else {
                        if (i != 1) break
                    }
                    i++
                }
            }
            json.decodeFromJsonElement(serializer, JsonArray(list))
        }

        descriptor in intDescriptors
                && el is JsonPrimitive
                && el.longOrNull == null
            -> {
            val newVal = el.double.roundToLong()
            json.decodeFromJsonElement(serializer, JsonPrimitive(newVal))
        }

        descriptor in floatDescriptors
                && el is JsonPrimitive
                && el.content.endsWith("inf")
            -> {
            val newVal = if (el.content == "-inf") Double.NEGATIVE_INFINITY else Double.POSITIVE_INFINITY
            json.decodeFromJsonElement(serializer, JsonPrimitive(newVal))
        }

        else -> null
    }
}

private fun getSerialName(
    klass: KClass<*>
): String =
    (klass.annotations.find { it is SerialName } as? SerialName)?.value
        ?: klass.simpleName!!


public open class JsonReaderSerializer<T : JsonReader>(private val klass: KClass<T>) : KSerializer<T> {
    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = object : SerialDescriptor {
        override val serialName: String = getSerialName(klass)
        override val kind: SerialKind get() = StructureKind.CLASS
        override val elementsCount: Int
        private val indices = mutableMapOf<String, Int>()
        private val elementNames: Array<String>
        private val elementDescriptors: Array<Any>

        init {
            val values = klass.java.getConstructor().newInstance().getDeclaredKeys()
                .entries.toList()
            elementsCount = values.size
            elementNames = Array(elementsCount) { i ->
                values[i].key
                    .also { indices[it] = i }
            }
            elementDescriptors = Array(elementsCount) { values[it].value.type }
        }


        override fun getElementDescriptor(index: Int): SerialDescriptor {
            val type = elementDescriptors[index]
            if (type is KType) {
                return serializer(type).descriptor.also { elementDescriptors[index] = it }
            }
            return type as SerialDescriptor
        }

        override fun getElementAnnotations(index: Int): List<Annotation> = emptyList()
        override fun getElementIndex(name: String): Int =
            indices[name] ?: throw SerializationException("Unknown key: $name")

        override fun getElementName(index: Int): String = elementNames[index]
        override fun isElementOptional(index: Int): Boolean = getElementDescriptor(index).isNullable

        override fun toString(): String = klass.qualifiedName!!
    }

    override fun deserialize(decoder: Decoder): T {
        val element = (decoder as JsonDecoder).decodeJsonElement().jsonObject
        val instance = klass.java.getConstructor().newInstance()
        instance.init(element)
        return instance
    }

    internal fun getFromJson(element: JsonObject): T {
        val instance = klass.java.getConstructor().newInstance()
        instance.init(element)
        return instance
    }

    override fun serialize(encoder: Encoder, value: T): Nothing = throw NotImplementedError()
}

public abstract class LiteralValue(public vararg val values: JsonPrimitive)

@OptIn(InternalSerializationApi::class)
public open class LiteralValueSerializer<T : LiteralValue>(clazz: KClass<T>) : KSerializer<T> {
    private val value = clazz.objectInstance!!

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("LiteralValue", SerialKind.CONTEXTUAL)
    override fun deserialize(decoder: Decoder): T {
        decoder as JsonDecoder
        val element = decoder.decodeJsonElement()
        if (element !is JsonPrimitive) {
            throw SerializationException("Expected a primitive value")
        }
        if (element !in value.values) {
            throw SerializationException("Expected one of ${value.values.joinToString()} but got $element")
        }
        return value
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder as JsonEncoder
        encoder.encodeJsonElement(value.values.first())
    }
}

public abstract class ArrayValue<T : Any>(public val values: List<T>) : List<T> by values {
    override fun toString(): String = "${this::class.simpleName}($values)"
    override fun equals(other: Any?): Boolean =
        other?.javaClass == javaClass && (other as ArrayValue<*>).values == values

    override fun hashCode(): Int = values.hashCode()
}

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
public abstract class ArrayValueSerializer<T : ArrayValue<*>>(
    klass: KClass<T>,
    elementType: KType,
) : KSerializer<T> {
    private val elementSerializer = serializer(elementType)
    private val constructor = klass.primaryConstructor!!
    override val descriptor: SerialDescriptor = buildSerialDescriptor("ArrayValue", SerialKind.CONTEXTUAL)
    override fun deserialize(decoder: Decoder): T {
        decoder as JsonDecoder
        val element = decoder.decodeJsonElement()
        if (element !is JsonArray) throw SerializationException("Expected an array")
        return constructor.call(element.map { decoder.json.decodeFromJsonElement(elementSerializer, it) })
    }

    override fun serialize(encoder: Encoder, value: T) {
        throw NotImplementedError()
    }
}


@OptIn(InternalSerializationApi::class)
@ExperimentalSerializationApi
public open class UnionSerializer<T : Any>(
    klass: KClass<T>,
    vararg subclasses: KClass<out T>
) : KSerializer<T> {
    private sealed interface JsonMatcher {
        val serializer: KSerializer<*>
        fun matches(element: JsonElement): Boolean
    }

    private data class ClassMatcher<T>(
        val keys: List<String>,
        override val serializer: KSerializer<T>,
    ) : JsonMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonObject &&
                    keys.any { it in element }
    }

    private sealed interface PrimitiveMatcher : JsonMatcher

    private data class StringMatcher(
        override val serializer: KSerializer<*>
    ) : JsonMatcher, PrimitiveMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && element.isString
    }

    private data class NumericMatcher(
        val klass: KClass<*>,
        override val serializer: KSerializer<*>,
    ) : JsonMatcher, PrimitiveMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive &&
                    !element.isString &&
                    element.doubleOrNull != null
    }

    private data class BooleanMatcher(
        override val serializer: KSerializer<*>
    ) : JsonMatcher, PrimitiveMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && !element.isString && element.booleanOrNull != null
    }

    private data class VectorMatcher(
        override val serializer: KSerializer<*>
    ) : JsonMatcher {
        override fun matches(element: JsonElement): Boolean = VectorSerializer.matches(element)
    }

    private data class LiteralMatcher(
        val from: LiteralValue,
        override val serializer: KSerializer<*>
    ) : JsonMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && element in from.values
    }

    private data class ArrayMatcher(
        val arrayType: KClass<ArrayValue<*>>
    ) : JsonMatcher {
        override val serializer by lazy { arrayType.serializer() }
        override fun matches(element: JsonElement): Boolean = element is JsonArray
    }

    private data class EnumMatcher(
        val enumType: KClass<*>
    ) : JsonMatcher {
        override val serializer by lazy { enumType.serializer() }
        private val values = enumType.java.enumConstants.map { (it as Enum<*>).name }
        override fun matches(element: JsonElement): Boolean = element is JsonPrimitive && element.isString
                && element.content in values
    }

    private val classMatchers: List<JsonMatcher>

    init {

        fun isClass(klass: KClass<*>): Boolean {
            return klass.isSubclassOf(JsonReader::class) || 
                    klass.isData && klass.objectInstance == null && !klass.isSubclassOf(ArrayValue::class)
        }

        fun isInnerInline(thisClass: KClass<*>): Boolean {
            return thisClass.isValue
                    && thisClass.primaryConstructor!!.parameters.first().type.classifier.let { it is KClass<*> && it in inlineTypes }
        }

        fun isLiteralValue(klass: KClass<*>): Boolean {
            return klass.objectInstance is LiteralValue
        }

        fun isArrayValue(klass: KClass<*>): Boolean {
            return klass.isSubclassOf(ArrayValue::class)
        }

        fun isOtherFirstMatchingSerializer(klass: KClass<*>): Boolean {
            return klass.serializerOrNull()?.let { it is UnionSerializer } == true
        }

        fun isEnum(klass: KClass<*>): Boolean {
            return klass.java.isEnum
        }

        val jsonReaderSerializer = subclasses
            .filter { isClass(it) }
            .associateWith {
                it.serializer()
            }
        // for each class, find keys that are _unique_ to that class (not shared with any other class)
        val classKeyCount = mutableMapOf<String, Int>()
        for ((_, serializer) in jsonReaderSerializer) {
            val descriptor = serializer.descriptor
            for (i in 0 until descriptor.elementsCount) {
                val name = descriptor.getElementName(i)
                classKeyCount[name] = (classKeyCount[name] ?: 0) + 1
            }
        }
        val matchers = mutableListOf<JsonMatcher>()
        outer@ for (subclass in subclasses) {
            if (isClass(subclass)) {
                val serializer = jsonReaderSerializer[subclass]!!
                val descriptor = serializer.descriptor
                for (i in 0..<descriptor.elementsCount) {
                    val elDescriptor = descriptor.getElementDescriptor(i)
                    if (!elDescriptor.isNullable) {
                        matchers.add(ClassMatcher(listOf(descriptor.getElementName(i)), serializer))
                        continue@outer
                    }
                }
                val allElements = descriptor.elementNames
                    .filter { classKeyCount[it] == 1 }
                if (allElements.isEmpty()) {
                    throw SerializationException("No unique keys found for class ${serializer.descriptor.serialName}")
                }
                matchers.add(ClassMatcher(allElements, serializer))
            } else if (isInnerInline(subclass)) {
                val serializer = subclass.serializer()
                matchers.add(
                    when (val type =
                        subclass.primaryConstructor!!.parameters.first().type.classifier as KClass<*>) {
                        String::class -> StringMatcher(serializer)
                        Boolean::class -> BooleanMatcher(serializer)
                        Vector::class -> VectorMatcher(serializer)
                        else -> NumericMatcher(type, serializer)
                    }
                )
            } else if (isArrayValue(subclass)) {
                @Suppress("UNCHECKED_CAST")
                matchers.add(ArrayMatcher(subclass as KClass<ArrayValue<*>>))
            } else if (isLiteralValue(subclass)) {
                val values = subclass.objectInstance as LiteralValue
                matchers.add(LiteralMatcher(values, subclass.serializer()))
            } else if (isOtherFirstMatchingSerializer(subclass)) {
                val serializer = subclass.serializer() as UnionSerializer<*>
                matchers.addAll(serializer.classMatchers)
            } else if (isEnum(subclass)) {
                matchers.add(EnumMatcher(subclass))
            } else {
                throw IllegalArgumentException("Subtype $subclass is not a JsonReader, inner inline primitive, LiteralValue, ArrayValue, or other FirstMatchingSerializer")
            }
        }

        // move primitive matchers to the end
        matchers.sortBy { it !is PrimitiveMatcher }
        this.classMatchers = matchers
    }


    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        getSerialName(klass),
        SerialKind.CONTEXTUAL
    )

    override fun serialize(encoder: Encoder, value: T) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val matcher = classMatchers.firstOrNull { it.matches(element) }
            ?: throw SerializationException(
                "In ${descriptor.serialName}, no matching type found for element: $element."
                        + " Expected one of: ${classMatchers.joinToString { it.serializer.descriptor.serialName }}\n"
            )
        @Suppress("UNCHECKED_CAST")
        return decoder.json.decodeFromJsonElement(matcher.serializer, element) as T
    }
}
