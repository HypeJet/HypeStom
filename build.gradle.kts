plugins {
    id("java")
    `maven-publish`
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.heroostech.citystom"
            artifactId = "CityStom"
            version = "v1.0.0"

            from(components["java"])
        }
    }
}