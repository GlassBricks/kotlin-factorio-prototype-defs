import java.io.File

fun main() {
    val url = object {}.javaClass.classLoader.getResource("prototype-api.json")!!
    val docs: ApiDocs = readDocs(url)

    val file = DeclarationsGenerator(docs).generate()
    val outDir = File("src/main/generated/").absoluteFile.normalize()
    println("Writing to ${outDir.path}")
    file.writeTo(outDir)
}
