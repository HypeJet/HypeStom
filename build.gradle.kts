plugins {
    id("java")
}

group = "me.heroostech.citystom"
version = "v1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}