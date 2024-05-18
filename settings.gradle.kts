rootProject.name = "Zirkonium"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

include("Zirkonium-API", "Zirkonium-Server", "Paper-API-Generator")
