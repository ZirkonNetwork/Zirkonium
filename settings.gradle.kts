pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "Zirkonium"
include("Zirkonium-API", "Zirkonium-Server")
