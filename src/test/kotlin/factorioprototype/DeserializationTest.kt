package factorioprototype

import defaultDataRaw
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromStream
import kotlin.test.Test
import kotlin.time.measureTime

class DeserializationTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun testDeserialize() {
        val dataRaw = defaultDataRaw!!
        val time = measureTime {
            val jsonElement = Json.decodeFromStream<JsonElement>(dataRaw.openStream().buffered())
            Json.decodeFromJsonElement(PrototypeData.serializer(), jsonElement)
        }
        println(time)
    }

}
