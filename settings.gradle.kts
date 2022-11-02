@file:Suppress("UnstableApiUsage")

rootProject.name = "ExampleExtension"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("minestom", "com.github.Minestom:Minestom:-SNAPSHOT")
            library("lombok", "org.projectlombok:lombok:1.18.24")
        }
    }
}