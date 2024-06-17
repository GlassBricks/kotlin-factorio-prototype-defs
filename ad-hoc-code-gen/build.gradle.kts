plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.0"
}

repositories {
    mavenCentral()
}
fun kotlinx(module: String, version: String? = null): String =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?: ""}"

dependencies {
    implementation(kotlin("reflect"))
    implementation("com.squareup:kotlinpoet:1.17.0")
    implementation(kotlinx("serialization-json", "1.7.0"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
