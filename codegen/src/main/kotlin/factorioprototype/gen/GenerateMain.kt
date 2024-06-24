package factorioprototype.gen

import java.io.File

fun main() {
    val url = object {}.javaClass.classLoader.getResource("prototype-api.json")!!
    val docs: ApiDocs = readDocs(url)

    val intfs = DeclarationsGenerator(docs).generate()
    val outDir = File("src/main/generated/").absoluteFile.normalize()
    println("Writing to ${outDir.path}")
    intfs.writeTo(outDir)
}
