package glassbricks.factorio.prototypes

import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule


public typealias UnknownOverriddenType = JsonElement

public typealias UnknownStringLiteral = String

public typealias UnknownTuple = JsonArray
public typealias Tuple1<T> = List<T>
public typealias Tuple2<T> = List<T>
public typealias Tuple3<T> = List<T>
public typealias Tuple4<T> = List<T>


@Serializable(with = ItemOrListSerializer::class)
public class ItemOrList<T>(private val value: List<T>) : List<T> by value {
    public constructor(value: T) : this(listOf(value))

    override fun toString(): String = value.toString()
    override fun equals(other: Any?): Boolean = value == other
    override fun hashCode(): Int = value.hashCode()
}
public typealias ItemOrTuple2<T> = ItemOrList<T>

private class ItemOrListSerializer<T>(private val itemSerializer: KSerializer<T>) : KSerializer<ItemOrList<T>> {
    private val listSerializer = ListSerializer(itemSerializer)
    override val descriptor: SerialDescriptor = listSerializer.descriptor
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

internal object NoiseFunctionApplicationDeserializer : DeserializationStrategy<NoiseFunctionApplication> {
    override val descriptor get() = NoiseFunctionApplication.serializer().descriptor
    override fun deserialize(decoder: Decoder): NoiseFunctionApplication {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        return factorioPrototypeJson.decodeFromJsonElement(NoiseFunctionApplication.serializer(), element)
    }
}


public val factorioPrototypeSerializersModule: SerializersModule = SerializersModule {
    fun maybeNoiseFunction(it: String?) = if (it == "function-application")
        NoiseFunctionApplicationDeserializer
    else null
    polymorphicDefaultDeserializer(NoiseNumber::class, ::maybeNoiseFunction)
    polymorphicDefaultDeserializer(NoiseExpression::class, ::maybeNoiseFunction)

    polymorphicDefaultDeserializer(EVEnergySource::class) { if (it == null) ElectricEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(BVEnergySource::class) { if (it == null) BurnerEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(EHFVEnergySource::class) { if (it == null) ElectricEnergySource.serializer() else null }
}

public val factorioPrototypeJson: Json = Json {
    serializersModule = factorioPrototypeSerializersModule
    allowSpecialFloatingPointValues = true
    ignoreUnknownKeys = true
}
