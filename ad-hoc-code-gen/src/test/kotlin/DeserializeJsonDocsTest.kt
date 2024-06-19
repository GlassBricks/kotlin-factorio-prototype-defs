import java.io.File
import java.net.URL
import kotlin.test.Test

class DeserializeJsonDocsTest {
    @Test
    fun run() {
        val url = URL("https://lua-api.factorio.com/latest/prototype-api.json")
        val docs: ApiDocs = json.decodeFromString(url.readText())

        val result = DeclarationGenerator(docs).generate()
        // write to file
        File("../src/main/kotlin/FactorioPrototypes.kt").writeText(result.toString())
    }
}
