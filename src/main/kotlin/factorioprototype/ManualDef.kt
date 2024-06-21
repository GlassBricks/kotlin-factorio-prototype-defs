package factorioprototype

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
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
            else -> ItemOrList(listOf(decoder.json.decodeFromJsonElement(itemSerializer, element)))
        }
    }
}


@Serializable(with = VectorSerializer::class)
data class Vector(
    val x: Double,
    val y: Double,
): StreamAttackParametersGunCenterShift

object VectorSerializer : KSerializer<Vector> {
    override val descriptor = buildClassSerialDescriptor("Vector") {
        element("x", Double.serializer().descriptor)
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
    val itemKlass: KClass<out JsonReader>,
    val fluidKlass: KClass<out JsonReader>,
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

object StreamAttackParametersGunCenterShiftSerializer : KSerializer<StreamAttackParametersGunCenterShift> {
    override val descriptor = buildClassSerialDescriptor("StreamAttackParametersGunCenterShift")

    override fun serialize(encoder: Encoder, value: StreamAttackParametersGunCenterShift) {
        throw NotImplementedError()
    }

    override fun deserialize(decoder: Decoder): StreamAttackParametersGunCenterShift {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val serializer = when (element) {
            is JsonArray -> VectorSerializer
            is JsonObject -> {
                if("north" in element) GunShift4Way.serializer()
                else if ("x" in element) Vector.serializer()
                else throw SerializationException("Can't interpret as Vector | GunShift4Way: $element")
            }
            else -> throw SerializationException("Unexpected element type: $element")
        }
        return decoder.json.decodeFromJsonElement(serializer, element)
    }
}

object TilePrototypeBuildSoundSerializer : KSerializer<TilePrototypeBuildSound> {
    override val descriptor get() = TileBuildSound.serializer().descriptor
    override fun serialize(encoder: Encoder, value: TilePrototypeBuildSound) {
        throw NotImplementedError()
    }
    override fun deserialize(decoder: Decoder): TilePrototypeBuildSound {
        return JsonReaderSerializer(TileBuildSound::class).deserialize(decoder)
    }
    
}


object NoiseFunctionApplicationDeserializer : DeserializationStrategy<NoiseFunctionApplication> {
    override val descriptor get() = NoiseFunctionApplication.serializer().descriptor
    override fun deserialize(decoder: Decoder): NoiseFunctionApplication {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        return json.decodeFromJsonElement(NoiseFunctionApplication.serializer(), element)
    }
}

// hacky workaround
val factorioPrototypeSerializersModule = SerializersModule {
    fun maybeNoiseFunction(it: String?) = if (it == "function-application")
        NoiseFunctionApplicationDeserializer
    else null
    polymorphicDefaultDeserializer(NoiseNumber::class, ::maybeNoiseFunction)
    polymorphicDefaultDeserializer(NoiseExpression::class, ::maybeNoiseFunction)

    polymorphicDefaultDeserializer(EVEnergySource::class) { if(it==null) ElectricEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(BVEnergySource::class) { if(it==null) BurnerEnergySource.serializer() else null }
    polymorphicDefaultDeserializer(EHFVEnergySource::class) { if(it==null) ElectricEnergySource.serializer() else null }
}

val json = Json {
    serializersModule = factorioPrototypeSerializersModule
    allowSpecialFloatingPointValues = true
}
