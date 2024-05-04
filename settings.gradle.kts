rootProject.name = "Zirkonium"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

include("zirkonium-api", "zirkonium-server", "paper-api-generator")
