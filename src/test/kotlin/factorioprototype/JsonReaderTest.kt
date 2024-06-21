package factorioprototype

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.test.Test


class JsonReaderTest {
    @Test
    fun test() {
        @Serializable
        data class Bar(val g: Int)

        class Foo : JsonReader() {
            val x: Int by fromJson()
            val y: String by fromJson()
            val z: Boolean? by fromJson()
            val list: List<Int?> by fromJson()
            val bar: Bar by fromJson()
        }


        val module = SerializersModule {
            contextual(JsonReaderDeserializer<Foo>())
        }
        val json = Json { serializersModule = module }
        // z is missing (nullable)
        val result: Foo = json.decodeFromString(
            """
            {
                "x": 1,
                "y": "foo",
                "list": [1, null, 3],
                "bar": {"g": 2}
            }
        """
        )
        assert(result.x == 1)
        assert(result.y == "foo")
        assert(result.z == null)
        assert(result.list == listOf(1, null, 3))
        assert(result.bar == Bar(2))
    }
}
