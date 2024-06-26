package glassbricks.factorio.prototypes

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream


public fun loadFactorioPrototypeData(jsonStr: String): PrototypeData {
    return factorioPrototypeJson.decodeFromString(PrototypeData.serializer(), jsonStr)
}

@ExperimentalSerializationApi
public fun loadFactorioPrototypeDataFromStream(stream: InputStream): PrototypeData {
    return factorioPrototypeJson.decodeFromStream(PrototypeData.serializer(), stream)
}
