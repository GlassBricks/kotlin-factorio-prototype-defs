plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    `java-library`
}

repositories {
    mavenCentral()
}

fun kotlinx(module: String, version: String? = null): String =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?: ""}"

dependencies {
    api(kotlinx("serialization-json", "1.7.0"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    explicitApi()
    jvmToolchain(17)
    sourceSets {
        val main by getting {
            kotlin.srcDir("src/main/generated")
        }
    }
}
