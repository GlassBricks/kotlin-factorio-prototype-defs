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
                        println(msg)
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


val a = UByte.serializer().descriptor

@OptIn(ExperimentalSerializationApi::class)
fun tryManuallyDeserializing(
    json: Json,
    el: JsonElement,
    serializer: KSerializer<*>
): Any? {
    val descriptor = serializer.descriptor.nonNullOriginal
    return when {
        descriptor.kind == StructureKind.LIST && el is JsonObject && (el.isEmpty() || "1" in el) -> {
            val list = buildList {
                var i = 1
                while (true) {
                    add(el[i.toString()] ?: break)
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

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
open class FirstMatchingSubclassSerializer<T : Any>(
    klass: KClass<T>,
    vararg subclasses: KClass<out T>
) : KSerializer<T> {
    private class ClassMatcher<T : JsonReader>(
        val keys: List<String>,
        val serializer: JsonReaderSerializer<T>,
    ) {
        fun matches(element: JsonObject): Boolean = keys.any { it in element }
    }

    init {
        subclasses.forEach {
            require(klass.isSubclassOf(JsonReader::class))
        }
    }

    private val classMatchers = run {
        val serializers = subclasses.map {
            @Suppress("UNCHECKED_CAST")
            JsonReaderSerializer(it as KClass<out JsonReader>)
        }
        // for each class, find keys that are _unique_ to that class (not shared with any other class)
        val keyCounts = mutableMapOf<String, Int>()
        for (serializer in serializers) {
            val descriptor = serializer.descriptor
            for (i in 0 until descriptor.elementsCount) {
                val name = descriptor.getElementName(i)
                keyCounts[name] = (keyCounts[name] ?: 0) + 1
            }
        }
        serializers.map {
            val descriptor = it.descriptor
            for (i in 0..<descriptor.elementsCount) {
                val elDescriptor = descriptor.getElementDescriptor(i)
                if (!elDescriptor.isNullable) {
                    return@map ClassMatcher(listOf(descriptor.getElementName(i)), it)
                }
            }
            val allElements = descriptor.elementNames
                .filter { keyCounts[it] == 1 }
            if (allElements.isEmpty()) {
                throw SerializationException("No unique keys found for class ${it.descriptor.serialName}")
            }
            ClassMatcher(allElements, it)
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
        val element = decoder.decodeJsonElement().jsonObject
        val matcher = classMatchers.firstOrNull { it.matches(element) }
            ?: throw SerializationException("No matching subclass found for $element")
        @Suppress("UNCHECKED_CAST")
        return matcher.serializer.deserialize(decoder) as T
    }
}
