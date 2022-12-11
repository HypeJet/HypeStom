plugins {
    id("java")
    `maven-publish`
    id ("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.heroostech.citystom"
version = "v1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lombok)
    implementation(libs.minimessage)
    annotationProcessor(libs.lombok)
}

project.tasks.findByName("jar")?.enabled = false

publishing {
    publications {
        create<MavenPublication>("maven") {
            afterEvaluate {
                val shadowJar = tasks.findByName("shadowJar")
                if (shadowJar == null) from(components["java"])
                else artifact(shadowJar)
            }
            groupId = "me.heroostech.citystom"
            artifactId = "CityStom"
            version = "v1.0.0"
        }
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    archiveClassifier.set("")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}