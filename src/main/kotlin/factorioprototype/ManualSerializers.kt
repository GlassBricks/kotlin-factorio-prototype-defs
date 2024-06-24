package factorioprototype

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlin.reflect.KClass


typealias UnknownOverriddenType = JsonElement
typealias UnknownUnion = JsonElement

typealias UnknownStringLiteral = String

typealias UnknownTuple = JsonArray
typealias Tuple1<T> = List<T>
typealias Tuple2<T> = List<T>
typealias Tuple3<T> = List<T>
typealias Tuple4<T> = List<T>


@Serializable(with = ItemOrListSerializer::class)
class ItemOrList<T>(private val value: List<T>) : List<T> by value {
    constructor(value: T) : this(listOf(value))
    override fun toString(): String = value.toString()
    override fun equals(other: Any?): Boolean = value == other
    override fun hashCode(): Int = value.hashCode()
}
typealias ItemOrTuple2<T> = ItemOrList<T>

class ItemOrListSerializer<T>(private val itemSerializer: KSerializer<T>) : KSerializer<ItemOrList<T>> {
    private val listSerializer = ListSerializer(itemSerializer)
    override val descriptor = listSerializer.descriptor
    override fun serialize(encoder: Encoder, value: ItemOrList<T>) {
        if (value.size == 1) {
            encoder.encodeSerializableValue(itemSerializer, value[0])
        } else {
            listSerializer.serialize(encoder, value)
        }
    }

    override fun deserialize(decoder: Decoder): ItemOrList<T> {
        decoder as JsonDecoder
        return when (val element = decoder.decodeJsonElement()) {
            is JsonArray -> ItemOrList(decoder.json.decodeFromJsonElement(listSerializer, element))
            else -> ItemOrList(decoder.json.decodeFromJsonElement(itemSerializer, element))
        }
    }
}

open class ItemSerializer<T : JsonReader>(
    private val klass: KClass<T>
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(klass.simpleName!!)

    override fun serialize(encoder: Encoder, value: T) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val newElement = if (element !is JsonArray) element
        else JsonObject(
            mapOf(
                "type" to JsonPrimitive("item"),
                "name" to element[0],
                "amount" to element[1]
            )
        )
        return decoder.json.decodeFromJsonElement(
            JsonReaderSerializer(klass),
            newElement
        )
    }
}

open class ItemFluidSerializer<T>(
    private val itemKlass: KClass<out JsonReader>,
    private val fluidKlass: KClass<out JsonReader>,
    val name: String
) : KSerializer<T> {
    override val descriptor = buildClassSerialDescriptor(name)

    override fun serialize(encoder: Encoder, value: T) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val serializer = when (element) {
            is JsonObject -> {
                when (val type = element["type"]?.jsonPrimitive?.content) {
                    "item", null -> ItemSerializer(itemKlass)
                    "fluid" -> JsonReaderSerializer(fluidKlass)
                    else -> throw SerializationException("Unknown ingredient type: $type")
                }
            }

            is JsonArray -> ItemSerializer(itemKlass)
            else -> throw SerializationException("Unexpected element: $element")
        }
        @Suppress("UNCHECKED_CAST")
        return decoder.json.decodeFromJsonElement(serializer, element) as T
    }
}

object ItemIngredientPrototypeSerializer : ItemSerializer<ItemIngredientPrototype>(ItemIngredientPrototype::class)
object IngredientPrototypeSerializer : ItemFluidSerializer<IngredientPrototype>(
    ItemIngredientPrototype::class,
    FluidIngredientPrototype::class,
    "IngredientPrototype"
)

object ItemProductPrototypeSerializer : ItemSerializer<ItemProductPrototype>(ItemProductPrototype::class)
object ProductPrototypeSerializer : ItemFluidSerializer<ProductPrototype>(
    ItemProductPrototype::class,
    FluidProductPrototype::class,
    "ProductPrototype"
)

object NoiseFunctionApplicationDeserializer : DeserializationStrategy<NoiseFunctionApplication> {
    override val descriptor get() = NoiseFunctionApplication.serializer().descriptor
    override fun deserialize(decoder: Decoder): NoiseFunctionApplication {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        return json.decodeFromJsonElement(NoiseFunctionApplication.serializer(), element)
    }
}


val factorioPrototypeSerializersModule = SerializersModule {
    fun maybeNoiseFunction(it: String?) = if (it == "function-application")
        NoiseFunctionApplicationDeserializer
    else null
    polymorphicDefaultDeserializer(NoiseNumber::class, ::maybeNoiseFunction)
    polymorphicDefaultDeserializer(NoiseExpression::class, ::maybeNoiseFunction)

    polymorphicDefaultDeserializer(EVEnergySource::class) { if (it == null) ElectricEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(BVEnergySource::class) { if (it == null) BurnerEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(EHFVEnergySource::class) { if (it == null) ElectricEnergySource.serializer() else null }
    
    // some mysterious bug due to caches and update order... here's a hacky workaround
    polymorphicDefaultDeserializer(Sound::class) { if (it == null) Sound.serializer() else null }
}

val json = Json {
    serializersModule = factorioPrototypeSerializersModule
    allowSpecialFloatingPointValues = true
}
