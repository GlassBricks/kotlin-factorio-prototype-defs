package glassbricks.factorio.prototypes

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

import kotlin.Double as KDouble

internal object LenientDoubleDeserializer : KSerializer<KDouble> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Double", PrimitiveKind.DOUBLE)
    override fun serialize(encoder: Encoder, value: KDouble) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): KDouble {
        decoder as JsonDecoder
        val element = decoder.decodeJsonElement().jsonPrimitive
        if (element.content == "inf") return Double.POSITIVE_INFINITY
        if (element.content == "-inf") return Double.NEGATIVE_INFINITY
        return element.double
    }
}

internal object LenientFloatDeserializer : KSerializer<Float> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Float", PrimitiveKind.FLOAT)
    override fun serialize(encoder: Encoder, value: Float) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): Float {
        decoder as JsonDecoder
        val element = decoder.decodeJsonElement().jsonPrimitive
        if (element.content == "inf") return Float.POSITIVE_INFINITY
        if (element.content == "-inf") return Float.NEGATIVE_INFINITY
        return element.double.toFloat()
    }
}


internal typealias Double = @Serializable(with = LenientDoubleDeserializer::class) kotlin.Double
internal typealias Float = @Serializable(with = LenientFloatDeserializer::class) kotlin.Float
