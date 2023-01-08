@file:Suppress("UnstableApiUsage")

rootProject.name = "CityStom"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("minestom", "com.github.Minestom:Minestom:24cc458659")
            library("lombok", "org.projectlombok:lombok:1.18.24")
            library("minimessage", "net.kyori:adventure-text-minimessage:4.11.0")
            library("toml", "com.moandjiezana.toml:toml4j:0.7.2")
            library("mongo", "org.mongodb:mongo-java-driver:3.12.11")
        }
    }
}