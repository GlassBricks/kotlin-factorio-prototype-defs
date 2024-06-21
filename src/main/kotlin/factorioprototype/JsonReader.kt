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
import kotlin.reflect.typeOf


internal var eagerInit = false

private class Uninitialized(
    val altName: String?,
    val type: KType
)


abstract class JsonReader {
    private lateinit var json: Json
    lateinit var jsonObj: JsonObject
        internal set

    private val values = mutableMapOf<String, Any?>()

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
            if (eagerInit) values[name] = Uninitialized(altName, type)
            ReadOnlyProperty { _, _ -> get(name, type) }
        }

    internal fun init(json: Json, jsonObj: JsonObject) {
        this.json = json
        this.jsonObj = jsonObj
        if (eagerInit) {
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

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
open class JsonReaderSerializer<T : JsonReader>(private val klass: KClass<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        getSerialName(klass),
        SerialKind.CONTEXTUAL
    )

    private fun getSerialName(klass: KClass<T>): String {
        return (klass.annotations.find { it is SerialName } as? SerialName)?.value
            ?: klass.simpleName!!
    }

    override fun deserialize(decoder: Decoder): T {
        val element = (decoder as JsonDecoder).decodeJsonElement()
        val instance = klass.java.getConstructor().newInstance()
        instance.init(decoder.json, element.jsonObject)
        return instance
    }

    override fun serialize(encoder: Encoder, value: T) = throw NotImplementedError()
}
