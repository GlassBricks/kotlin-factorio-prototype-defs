package glassbricks.factorio.prototypes

import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlin.reflect.KClass


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

internal open class ItemSerializer<T : JsonReader>(
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

internal open class ItemFluidSerializer<T>(
    private val itemKlass: KClass<out JsonReader>,
    private val fluidKlass: KClass<out JsonReader>,
    public val name: String
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(name)

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

internal object ItemIngredientPrototypeSerializer :
    ItemSerializer<ItemIngredientPrototype>(ItemIngredientPrototype::class)

internal object IngredientPrototypeSerializer : ItemFluidSerializer<IngredientPrototype>(
    ItemIngredientPrototype::class,
    FluidIngredientPrototype::class,
    "IngredientPrototype"
)

internal object ItemProductPrototypeSerializer : ItemSerializer<ItemProductPrototype>(ItemProductPrototype::class)
internal object ProductPrototypeSerializer : ItemFluidSerializer<ProductPrototype>(
    ItemProductPrototype::class,
    FluidProductPrototype::class,
    "ProductPrototype"
)

internal open class ShorthandSerializer<T : JsonReader>(
    klass: KClass<T>,
    override val descriptor: SerialDescriptor
) : KSerializer<T> {
    override fun serialize(encoder: Encoder, value: T) {
        throw NotImplementedError()
    }

    private val jsonReaderSerializer = JsonReaderSerializer(klass)

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val obj = when (val element = decoder.decodeJsonElement()) {
            is JsonArray -> JsonObject(buildMap {
                descriptor.elementNames.forEachIndexed { index, name ->
                    put(name, element[index])
                }
            })

            is JsonObject -> element
            else -> throw SerializationException("Unexpected element: $element")
        }
        return jsonReaderSerializer.getFromJson(obj)
    }

}

internal object SpawnPointSerializer : ShorthandSerializer<SpawnPoint>(
    SpawnPoint::class,
    buildClassSerialDescriptor("SpawnPoint") {
        element<Double>("evolution_factor")
        element<Double>("spawn_weight")
    })

internal object UnitSpawnDefinitionSerializer : ShorthandSerializer<UnitSpawnDefinition>(
    UnitSpawnDefinition::class,
    buildClassSerialDescriptor("UnitSpawnDefinition") {
        element<EntityID>("unit")
        element<List<SpawnPoint>>("spawn_points")
    }
)


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
}
