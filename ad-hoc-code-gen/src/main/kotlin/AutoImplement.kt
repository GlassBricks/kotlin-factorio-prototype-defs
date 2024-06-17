import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties


fun autoImplementInterface(
    intf: KClass<*>,
): TypeSpec {
    require(intf.isAbstract && intf.constructors.isEmpty())
    val b = TypeSpec.classBuilder(intf.simpleName!! + "Impl")
    b.addSuperinterface(intf)
    val properties = intf.memberProperties
//    println(properties)
    for (prop in properties) {
        val name = prop.name
        b.addProperty(
            PropertySpec.builder(
                name,
                prop.returnType.asTypeName(), listOf(KModifier.OVERRIDE)
            )
                .initializer(name)
                .build()
        )
    }
    b.primaryConstructor(
        FunSpec.constructorBuilder()
            .addParameters(properties.map {
                ParameterSpec.builder(it.name, it.returnType.asTypeName()).build()
            })
            .build()
    )

    return b.build()
}
