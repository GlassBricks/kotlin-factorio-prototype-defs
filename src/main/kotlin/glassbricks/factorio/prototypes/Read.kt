package glassbricks.factorio.prototypes

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import java.io.File
import java.io.InputStream


public fun loadFactorioPrototypes(json: JsonObject): PrototypeData {
    return PrototypeData(json)
}

public fun loadFactorioPrototypes(jsonStr: String): PrototypeData {
    return loadFactorioPrototypes(factorioPrototypeJson.parseToJsonElement(jsonStr).jsonObject)
}

@OptIn(ExperimentalSerializationApi::class)
public fun loadFactorioPrototypesFromStream(stream: InputStream): PrototypeData {
    return loadFactorioPrototypes(
        factorioPrototypeJson.decodeFromStream(JsonObject.serializer(), stream)
    )
}

public fun loadFactorioPrototypesFromFile(file: File): PrototypeData {
    return loadFactorioPrototypesFromStream(file.inputStream())
}
