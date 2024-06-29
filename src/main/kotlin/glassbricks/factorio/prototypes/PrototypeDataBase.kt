package glassbricks.factorio.prototypes

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public abstract class PrototypeDataBase internal constructor(internal val json: JsonObject) {
    protected inline operator fun <reified T> getValue(thisRef: Any?, property: KProperty<*>): Map<String, T> =
        getFromJson(property.name, typeOf<T>())

    protected fun <T> getFromJson(
        name: String,
        elementType: KType,
    ): Map<String, T> {
        val value = json[name]?.jsonObject ?: return emptyMap()
        @Suppress("UNCHECKED_CAST")
        val elSerializer = serializer(elementType) as KSerializer<T>
        return factorioPrototypeJson.decodeFromJsonElement(MapSerializer(String.serializer(), elSerializer), value)
    }

}
