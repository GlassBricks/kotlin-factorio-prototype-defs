package factorioprototype

import glassbricks.factorio.prototypes.JsonReader
import glassbricks.factorio.prototypes.JsonReaderSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test


@Serializable
data class Bar(val g: Int)

@Serializable(with = Foo.Serializer::class)
class Foo : JsonReader() {
    val x: Int by fromJson()
    val y: String by fromJson()
    val z: Boolean? by fromJson()
    val list: List<Int?> by fromJson()
    val bar: Bar by fromJson()

    object Serializer : JsonReaderSerializer<Foo>(Foo::class)
}

class JsonReaderTest {
    @Test
    fun test() {
        val json = Json
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
