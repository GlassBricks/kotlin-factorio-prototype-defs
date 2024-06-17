import com.squareup.kotlinpoet.asClassName
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*


typealias UnknownUnion = JsonElement
typealias UnknownTuple = JsonArray
typealias InnerStringUnion = String
typealias UnknownStringLiteral = String
typealias UnknownBooleanLiteral = Boolean
typealias UnknownIntegerLiteral = Number

val builtinMap = mapOf(
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
    "Vector" to Vector::class.asClassName(),
    "Vector3D" to Vector3D::class.asClassName(),
)
val toIgnore = setOf("DataExtendMethod")

@Serializable(with = VectorSerializer::class)
class Vector(
    val x: Double,
    val y: Double,
)

object VectorSerializer : KSerializer<Vector> {
    override val descriptor = buildClassSerialDescriptor("Vector") {
        element("x", Double.serializer().descriptor)
        element("y", Double.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: Vector) {
        throw NotImplementedError("Not implemented")
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
class Vector3D(
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
        throw NotImplementedError("Not implemented")
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
