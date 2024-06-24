package factorioprototype

import defaultDataRaw
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.elementDescriptors
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromStream
import kotlin.test.Test
import kotlin.time.measureTime

@OptIn(ExperimentalSerializationApi::class)
class DeserializationTest {
    
    @Test
    fun canInstantiateSerializer() {
        val serializer = Sprite.Serializer
//        println(serializer.descriptor.elementDescriptors.toList())
        for(i in 0 until serializer.descriptor.elementsCount) {
            println(serializer.descriptor.getElementName(i))
            println(serializer.descriptor.getElementDescriptor(i))
        }
    }
    @Test
    fun testDeserializePolymorphic() {
        val attackParameters = json.decodeFromString(
            AttackParameters.serializer(), """
            {
                "type": "beam",
                "range": 10,
                "cooldown": 1.0,
                "damage": 10
            }
        """
        )
        assert(attackParameters is BeamAttackParameters)
    }

    @Test
    fun testDeserializeNoiseFunction() {
        val noiseFunction = json.decodeFromString(
            NoiseExpression.serializer(), """
            {
                "type": "function-application",
                "function_name": "cos",
                "arguments": [{
                    "type": "literal-number",
                    "literal_value": 1.0
                }]
            }
        """
        )
        assert(noiseFunction is NoiseFunctionCos)
    }


    @Test
    fun testDataRaw() {
        val dataRaw = defaultDataRaw!!
        eagerInit = true
        val time = measureTime {
            val jsonElement = json.decodeFromStream<JsonElement>(dataRaw.openStream().buffered())
            json.decodeFromJsonElement(PrototypeData.serializer(), jsonElement)
        }
        println(time)
    }

}
