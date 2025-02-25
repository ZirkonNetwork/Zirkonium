import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

if (!file(".git").exists()) {
    val errorText = """
        
        =====================[ ERROR ]=====================
         The Zirkonium project directory is not a properly cloned Git repository.
         
         In order to build Zirkonium from source you must clone
         the Zirkonium repository using Git, not download a code
         zip from GitHub.
         
         Built Zirkonium jars are available for download at
         https://github.com/ZirkonNetwork/Zirkonium/actions
         
         See https://github.com/PaperMC/Paper/blob/main/CONTRIBUTING.md
         for further information on building and modifying Paper forks.
        ===================================================
    """.trimIndent()
    error(errorText)
}

rootProject.name = "zirkonium"

for (name in listOf("zirkonium-api", "zirkonium-server")) {
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}