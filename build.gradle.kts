plugins {
    id("java")
    `maven-publish`
    id ("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.labgames.nextlib"
version = "1.4.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lombok)
    compileOnly(libs.mongo)
    implementation(libs.minimessage)
    implementation(libs.toml)
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
            groupId = "org.labgames.nextlib"
            artifactId = "NextLib"
            version = "1.4.1"
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