package factorioprototype

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

@Serializable(with = VectorSerializer::class)
data class Vector(
    val x: Double,
    val y: Double,
)

object VectorSerializer : KSerializer<Vector> {
    override val descriptor = buildClassSerialDescriptor("Vector") {
        element("x", Double.Companion.serializer().descriptor)
        element("y", Double.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: Vector) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): Vector {
        require(decoder is JsonDecoder)
        val x: Double
        val y: Double
        when (val json = decoder.decodeJsonElement()) {
            is JsonObject -> {
                x = json["x"]!!.jsonPrimitive.double
                y = json["y"]!!.jsonPrimitive.double
            }

            is JsonArray -> {
                x = json[0].jsonPrimitive.double
                y = json[1].jsonPrimitive.double
            }

            else -> throw IllegalArgumentException("Unknown literal type: $json")
        }
        return Vector(x, y)
    }
}

@Serializable(with = Vector3DSerializer::class)
data class Vector3D(
    val x: Double,
    val y: Double,
    val z: Double,
)

object Vector3DSerializer : KSerializer<Vector3D> {
    override val descriptor = buildClassSerialDescriptor("Vector3D") {
        element("x", Double.serializer().descriptor)
        element("y", Double.serializer().descriptor)
        element("z", Double.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: Vector3D) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): Vector3D {
        require(decoder is JsonDecoder)
        val x: Double
        val y: Double
        val z: Double
        when (val json = decoder.decodeJsonElement()) {
            is JsonObject -> {
                x = json["x"]!!.jsonPrimitive.double
                y = json["y"]!!.jsonPrimitive.double
                z = json["z"]!!.jsonPrimitive.double
            }

            is JsonArray -> {
                x = json[0].jsonPrimitive.double
                y = json[1].jsonPrimitive.double
                z = json[2].jsonPrimitive.double
            }

            else -> throw IllegalArgumentException("Unknown literal type: $json")
        }
        return Vector3D(x, y, z)
    }
}

@Serializable(with = BoundingBoxSerializer::class)
data class BoundingBox(
    val left_top: Vector,
    val right_bottom: Vector,
)

object BoundingBoxSerializer : KSerializer<BoundingBox> {
    override val descriptor = buildClassSerialDescriptor("BoundingBox") {
        element("left_top", Vector.serializer().descriptor)
        element("right_bottom", Vector.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: BoundingBox) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): BoundingBox {
        require(decoder is JsonDecoder)
        val left_top: Vector
        val right_bottom: Vector
        when (val json = decoder.decodeJsonElement()) {
            is JsonObject -> {
                left_top = json["left_top"]!!.let { decoder.json.decodeFromJsonElement(Vector.serializer(), it) }
                right_bottom =
                    json["right_bottom"]!!.let { decoder.json.decodeFromJsonElement(Vector.serializer(), it) }
            }

            is JsonArray -> {
                left_top = decoder.json.decodeFromJsonElement(Vector.serializer(), json[0])
                right_bottom = decoder.json.decodeFromJsonElement(Vector.serializer(), json[1])
            }

            else -> throw IllegalArgumentException("Unknown literal type: $json")
        }
        return BoundingBox(left_top, right_bottom)
    }
}

@Serializable(with = ColorSerializer::class)
data class Color(
    val r: Byte,
    val g: Byte,
    val b: Byte,
    val a: Byte,
)

private fun Double.coerceToByte(): Byte =
    if (this < 0) 0.toByte() else if (this > 255) 255.toByte() else this.toInt().toByte()

object ColorSerializer : KSerializer<Color> {
    override val descriptor = buildClassSerialDescriptor("Color") {
        element("r", Byte.serializer().descriptor)
        element("g", Byte.serializer().descriptor)
        element("b", Byte.serializer().descriptor)
        element("a", Byte.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): Color {
        require(decoder is JsonDecoder)
        val r: Double
        val g: Double
        val b: Double
        val a: Double?
        when (val json = decoder.decodeJsonElement()) {
            is JsonObject -> {
                r = json["r"]?.jsonPrimitive?.double ?: 0.0
                g = json["g"]?.jsonPrimitive?.double ?: 0.0
                b = json["b"]?.jsonPrimitive?.double ?: 0.0
                a = json["a"]?.jsonPrimitive?.double ?: 0.0
            }

            is JsonArray -> {
                r = json[0].jsonPrimitive.double
                g = json[1].jsonPrimitive.double
                b = json[2].jsonPrimitive.double
                a = json.getOrNull(3)?.jsonPrimitive?.double
            }

            else -> throw IllegalArgumentException("Unexpected type for Color: $json")
        }
        if (r > 1 || g > 1 || b > 1 || a != null && a > 1) {
            return Color(
                r.coerceToByte(),
                g.coerceToByte(),
                b.coerceToByte(),
                a?.coerceToByte() ?: 255.toByte()
            )
        }
        return Color(
            (r * 255).coerceToByte(),
            (g * 255).coerceToByte(),
            (b * 255).coerceToByte(),
            a?.let { (it * 255).coerceToByte() } ?: 255.toByte())
    }


}
