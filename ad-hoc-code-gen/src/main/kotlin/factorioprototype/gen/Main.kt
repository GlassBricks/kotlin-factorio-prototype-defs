package factorioprototype.gen

import java.io.File

fun main() {
    val url = object {}.javaClass.classLoader.getResource("prototype-api.json")!!
    val docs: ApiDocs = json.decodeFromString(url.readText())

    val intfs = InterfaceGenerator(docs).generate()
    val file = File("src/main/kotlin/").absoluteFile.normalize()
    println("Writing to ${file.path}")
    intfs.declarationsFile.writeTo(file)
}
