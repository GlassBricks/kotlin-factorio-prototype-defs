package factorioprototype

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf


abstract class JsonReader {
    private lateinit var json: Json
    lateinit var jsonObj: JsonObject
        internal set

    internal fun init(json: Json, jsonObj: JsonObject) {
        this.json = json
        this.jsonObj = jsonObj
    }

    private val values = mutableMapOf<String, Any?>()

    @Suppress("UNCHECKED_CAST")
    protected fun <T> get(name: String, type: KType): T {
        if (name in values) return values[name] as T
        val value = run {
            val serializer = json.serializersModule.serializer(type) as KSerializer<T>
            val el = jsonObj[name]
            if (el == null) {
                if (!type.isMarkedNullable) {
                    throw SerializationException("Missing required field: $name")
                }
                return@run null
            }
            return@run json.decodeFromJsonElement(serializer, el)
        } as T
        values[name] = value
        return value
    }

    protected inline fun <reified T> get(name: String): T = get(name, typeOf<T>()) as T

    protected fun <T> fromJson(type: KType): ReadOnlyProperty<Any, T> {
        return ReadOnlyProperty { _, property -> get(property.name, type) }
    }

    protected inline fun <reified T> fromJson(): ReadOnlyProperty<Any, T> = fromJson(typeOf<T>())

    override fun toString(): String = "${this::class.simpleName}()"
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@Suppress("FunctionName")
fun <T : JsonReader> JsonReaderDeserializer(class_: Class<T>): KSerializer<T> = object : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = buildSerialDescriptor(
            class_.simpleName ?: "JsonReader",
            SerialKind.CONTEXTUAL
        )

    override fun deserialize(decoder: Decoder): T {
        val element = (decoder as JsonDecoder).decodeJsonElement()
        val instance = class_.getConstructor().newInstance()
        instance.init(decoder.json, element.jsonObject)
        return instance
    }

    override fun serialize(encoder: Encoder, value: T) = throw NotImplementedError()
}

@Suppress("FunctionName")
inline fun <reified T : JsonReader> JsonReaderDeserializer(): KSerializer<T> = JsonReaderDeserializer(T::class.java)
