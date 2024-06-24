package factorioprototype

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
import kotlin.reflect.typeOf


internal var eagerInit = false

abstract class JsonReader {
    private lateinit var json: Json
    lateinit var jsonObj: JsonObject
        internal set

    private val values = mutableMapOf<String, Any?>()


    internal class Uninitialized(
        val altName: String?,
        val type: KType
    )

    @Suppress("UNCHECKED_CAST")
    internal fun getDeclaredKeys(): Map<String, Uninitialized> = values as Map<String, Uninitialized>

    @Suppress("UNCHECKED_CAST")
    protected fun <T> get(name: String, type: KType, altName: String? = null): T {
        if (name in values) {
            val value = values[name]
            if (value !is Uninitialized) {
                return value as T
            }
        }
        val value = run {
            val serializer = json.serializersModule.serializer(type) as KSerializer<T>
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
                return@run null
            }

            tryManuallyDeserializing(json, el, serializer)?.let { return@run it }

            return@run json.decodeFromJsonElement(serializer, el)
        } as T
        values[name] = value
        return value
    }


    protected inline fun <reified T> get(name: String): T = get(name, typeOf<T>()) as T

    protected fun <T> fromJson(altName: String? = null): PropertyDelegateProvider<Any, ReadOnlyProperty<Any, T>> =
        PropertyDelegateProvider { _, property ->
            val type = property.returnType
            val name = property.name
            values[name] = Uninitialized(altName, type)
            ReadOnlyProperty { _, _ -> get(name, type) }
        }

    internal fun init(json: Json, jsonObj: JsonObject, eager: Boolean = eagerInit) {
        this.json = json
        this.jsonObj = jsonObj
        if (eager) {
            for ((name, value) in values) {
                if (value is Uninitialized) {
                    get<Any>(name, value.type, value.altName)
                }
            }
        }
    }

    override fun toString(): String = "${this::class.simpleName}()"
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

private val primitiveTypes = setOf(
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
    Boolean::class
)

@OptIn(ExperimentalSerializationApi::class)
fun tryManuallyDeserializing(
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
                    if (element != null) add(element)
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


open class JsonReaderSerializer<T : JsonReader>(private val klass: KClass<T>) : KSerializer<T> {
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
        instance.init(decoder.json, element)
        return instance
    }

    override fun serialize(encoder: Encoder, value: T) = throw NotImplementedError()
}

abstract class LiteralValue(vararg val values: JsonPrimitive)

@OptIn(InternalSerializationApi::class)
open class LiteralValueSerializer<T : LiteralValue>(clazz: KClass<T>) : KSerializer<T> {
    val value = clazz.objectInstance!!

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


@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
open class FirstMatchingSerializer<T : Any>(
    klass: KClass<T>,
    vararg subclasses: KClass<out T>
) : KSerializer<T> {

    private sealed interface SubTypeMatcher {
        val serializer: KSerializer<*>
        fun matches(element: JsonElement): Boolean
    }

    private data class ClassMatcher<T : JsonReader>(
        val keys: List<String>,
        override val serializer: JsonReaderSerializer<T>,
    ) : SubTypeMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonObject &&
                    keys.any { it in element }
    }

    private data class StringMatcher(
        override val serializer: KSerializer<*>
    ) : SubTypeMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && element.isString
    }

    private data class NumericMatcher(
        val klass: KClass<*>,
        override val serializer: KSerializer<*>,
    ) : SubTypeMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive &&
                    !element.isString &&
                    element.doubleOrNull != null
    }

    private data class BooleanMatcher(
        override val serializer: KSerializer<*>
    ) : SubTypeMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && !element.isString && element.booleanOrNull != null
    }

    private data class LiteralMatcher(
        val from: LiteralValue,
        override val serializer: KSerializer<*>
    ) : SubTypeMatcher {
        override fun matches(element: JsonElement): Boolean =
            element is JsonPrimitive && element in from.values
    }

    init {

        fun isJsonReader(klass: KClass<*>): Boolean {
            return klass.isSubclassOf(JsonReader::class)
        }

        fun isInnerInlinePrimitive(thisClass: KClass<*>): Boolean {
            return thisClass in klass.nestedClasses
                    && thisClass.isValue
                    && thisClass.primaryConstructor!!
                .parameters.first().type.classifier.let { it is KClass<*> && it in primitiveTypes }
        }

        fun isLiteralValue(klass: KClass<*>): Boolean {
            return klass.objectInstance is LiteralValue
        }

        for (subclass in subclasses) {
            require(
                isJsonReader(subclass)
                        || isInnerInlinePrimitive(subclass)
                        || isLiteralValue(subclass)
            ) {
                "Subclass $subclass is not a JsonReader, inner inline primitive, or LiteralValue"
            }
        }
    }

    private val classMatchers = run {
        val classSerializers = subclasses
            .filter { it.isSubclassOf(JsonReader::class) }
            .associateWith {
                @Suppress("UNCHECKED_CAST")
                JsonReaderSerializer(it as KClass<out JsonReader>)
            }
        // for each class, find keys that are _unique_ to that class (not shared with any other class)
        val classKeyCount = mutableMapOf<String, Int>()
        for ((_, serializer) in classSerializers) {
            val descriptor = serializer.descriptor
            for (i in 0 until descriptor.elementsCount) {
                val name = descriptor.getElementName(i)
                classKeyCount[name] = (classKeyCount[name] ?: 0) + 1
            }
        }
        subclasses.map { klass ->
            if (klass in classSerializers) {
                val serializer = classSerializers[klass]!!
                val descriptor = serializer.descriptor
                for (i in 0..<descriptor.elementsCount) {
                    val elDescriptor = descriptor.getElementDescriptor(i)
                    if (!elDescriptor.isNullable) {
                        return@map ClassMatcher(listOf(descriptor.getElementName(i)), serializer)
                    }
                }
                val allElements = descriptor.elementNames
                    .filter { classKeyCount[it] == 1 }
                if (allElements.isEmpty()) {
                    throw SerializationException("No unique keys found for class ${serializer.descriptor.serialName}")
                }
                ClassMatcher(allElements, serializer)
            } else if (klass.isValue) {
                val serializer = klass.serializer()
                when (val type = klass.primaryConstructor!!.parameters.first().type.classifier as KClass<*>) {
                    String::class -> StringMatcher(serializer)
                    Boolean::class -> BooleanMatcher(serializer)
                    else -> NumericMatcher(type, serializer)
                }
            } else {
                // class is literal
                val values = klass.objectInstance as LiteralValue
                LiteralMatcher(values, klass.serializer())
            }
        }
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
            ?: throw SerializationException("No matching subclass found for $element")
        @Suppress("UNCHECKED_CAST")
        return matcher.serializer.deserialize(decoder) as T
    }
}
