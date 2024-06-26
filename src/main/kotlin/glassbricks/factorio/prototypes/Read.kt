package glassbricks.factorio.prototypes

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.InputStream


public fun loadFactorioPrototypes(jsonStr: String): PrototypeData {
    return factorioPrototypeJson.decodeFromString(PrototypeData.serializer(), jsonStr)
}

@OptIn(ExperimentalSerializationApi::class)
public fun loadFactorioPrototypesFromStream(stream: InputStream): PrototypeData {
    return factorioPrototypeJson.decodeFromStream(PrototypeData.serializer(), stream)
}

public fun loadFactorioPrototypesFromFile(file: File): PrototypeData {
    return loadFactorioPrototypesFromStream(file.inputStream())
}
