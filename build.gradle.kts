plugins {
    id("java")
}

group = "me.heroostech.exampleextension"
version = "v1.0.0"

repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}