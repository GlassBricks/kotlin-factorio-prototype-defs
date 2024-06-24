@file:OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)

package factorioprototype.gen

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import java.net.URL

@Serializable
class ApiDocs(
    val application: String,
    val application_version: String,
    val api_version: Int,
    val stage: String,
    val prototypes: List<Prototype>,
    val types: List<Concept>
)

sealed interface ProtoOrConcept {
    val name: String
    val order: Int
    val description: String
    val lists: List<String>?
    val examples: List<String>?
    val images: List<Image>?
    val parent: String?
    val abstract: Boolean
    val properties: List<Property>?
}

@Serializable
class Prototype(
    override val name: String,
    override val order: Int,
    override val description: String,
    override val lists: List<String>? = null,
    override val examples: List<String>? = null,
    override val images: List<Image>? = null,
    val visibility: List<String>? = null,
    override val parent: String? = null,
    override val abstract: Boolean,
    val typename: String? = null,
    val instance_limit: Int? = null,
    val deprecated: Boolean,
    override val properties: List<Property>,
    val custom_properties: CustomProperties? = null
) : ProtoOrConcept {
    override fun toString(): String = "Prototype(name='$name')"
}

@Serializable
class Concept(
    override val name: String,
    override val order: Int,
    override val description: String,
    override val lists: List<String>? = null,
    override val examples: List<String>? = null,
    override val images: List<Image>? = null,
    override val parent: String? = null,
    override val abstract: Boolean,
    val inline: Boolean,
    val type: TypeDefinition,
    override val properties: List<Property>? = null
) : ProtoOrConcept {
    override fun toString(): String = "Concept(name='$name')"
}

@Serializable
class Property(
    val name: String,
    val order: Int,
    val description: String,
    val lists: List<String>? = null,
    val examples: List<String>? = null,
    val images: List<Image>? = null,
    val visibility: List<String>? = null,
    val alt_name: String? = null,
    val override: Boolean,
    var type: TypeDefinition,
    val optional: Boolean,
    val default: DefaultValue? = null
)

@Serializable(with = DefaultValueSerializer::class)
sealed interface DefaultValue

@Serializable
data class DescriptionDefault(val value: String) : DefaultValue

@Serializable
data class LiteralDefault(val value: JsonPrimitive) : DefaultValue

object DefaultValueSerializer : KSerializer<DefaultValue> {
    override val descriptor: SerialDescriptor
        get() = buildSerialDescriptor("DefaultValue", SerialKind.CONTEXTUAL)

    override fun deserialize(decoder: Decoder): DefaultValue {
        require(decoder is JsonDecoder) { "This deserializer can only be used with Json" }
        val json = decoder.decodeJsonElement()
        return if (json is JsonPrimitive) {
            DescriptionDefault(json.jsonPrimitive.content)
        } else {
            val obj = json.jsonObject
            require(obj["complex_type"]?.jsonPrimitive?.content == "literal")
            LiteralDefault(obj["value"]!!.jsonPrimitive)
        }
    }

    override fun serialize(encoder: Encoder, value: DefaultValue) {
        throw NotImplementedError("Serialization is not supported")
    }
}

@Serializable(with = TypeDefinitionSerializer::class)
sealed interface TypeDefinition

@Serializable
data class BasicType(val value: String) : TypeDefinition

@Serializable
data class ArrayType(val value: TypeDefinition) : TypeDefinition

@Serializable
data class DictType(val key: TypeDefinition, val value: TypeDefinition) : TypeDefinition

@Serializable
data class TupleType(val values: List<TypeDefinition>) : TypeDefinition

@Serializable
data class UnionType(val options: List<TypeDefinition>, val full_format: Boolean) : TypeDefinition

@Serializable
data class LiteralType(val value: JsonPrimitive, val description: String? = null) : TypeDefinition

@Serializable
data class TypeType(val value: TypeDefinition) : TypeDefinition

@Serializable
data object StructType : TypeDefinition

object TypeDefinitionSerializer : KSerializer<TypeDefinition> {
    override val descriptor: SerialDescriptor
        get() = buildSerialDescriptor("TypeDefinition", SerialKind.CONTEXTUAL)

    override fun deserialize(decoder: Decoder): TypeDefinition {
        require(decoder is JsonDecoder) { "This deserializer can only be used with Json" }
        val json = decoder.decodeJsonElement()
        if (json is JsonPrimitive) {
            return BasicType(json.jsonPrimitive.content)
        }
        val obj = json.jsonObject
        val serializer = when (obj["complex_type"]?.jsonPrimitive?.content) {
            "array" -> ArrayType.serializer()
            "dictionary" -> DictType.serializer()
            "tuple" -> TupleType.serializer()
            "union" -> UnionType.serializer()
            "literal" -> LiteralType.serializer()
            "type" -> TypeType.serializer()
            "struct" -> StructType.serializer()
            else -> throw IllegalArgumentException("Unknown type: ${obj["type"]}")
        }
        return decoder.json.decodeFromJsonElement(serializer, json)
    }

    override fun serialize(encoder: Encoder, value: TypeDefinition) {
        throw NotImplementedError("Serialization is not supported")
    }
}


@Serializable
class Image(
    val filename: String,
    val caption: String? = null
)

@Serializable
class CustomProperties(
    val description: String,
    val lists: List<String>? = null,
    val examples: List<String>? = null,
    val images: List<Image>? = null,
    val key_type: TypeDefinition,
    val value_type: TypeDefinition
)


fun TypeDefinition.innerType(): TypeDefinition = when (this) {
    is TypeType -> value
    else -> this
}

inline fun <T> List<T>.pairwiseEquals(other: List<T>, predicate: (T, T) -> Boolean): Boolean {
    if (size != other.size) return false
    return zip(other).all { (a, b) -> predicate(a, b) }
}

fun TypeDefinition.typeEquals(other: TypeDefinition): Boolean {
    val other = other.innerType()
    return when (val t = innerType()) {
        is BasicType -> other is BasicType && t.value == other.value
        is ArrayType -> other is ArrayType && t.value.typeEquals(other.value)
        is DictType -> other is DictType && t.key.typeEquals(other.key) && t.value.typeEquals(other.value)
        is TupleType -> other is TupleType && t.values.pairwiseEquals(other.values) { a, b -> a.typeEquals(b) }
        is UnionType -> other is UnionType && t.options.pairwiseEquals(other.options) { a, b -> a.typeEquals(b) }
        is LiteralType -> other is LiteralType && t.value == other.value
        is TypeType, StructType -> false
    }
}


private val json = Json {
    ignoreUnknownKeys = true
}

fun readDocs(url: URL): ApiDocs {
    return json.decodeFromStream<ApiDocs>(url.openStream())
}
