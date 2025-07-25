import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("io.papermc.paperweight.patcher") version "2.0.0-beta.17"
}

paperweight {
    upstreams.register("leaf") {
        repo = github("Winds-Studio", "Leaf")
        ref = providers.gradleProperty("leafCommit")

        patchFile {
            path = "leaf-server/build.gradle.kts"
            outputFile = file("zirkonium-server/build.gradle.kts")
            patchFile = file("zirkonium-server/build.gradle.kts.patch")
        }
        patchFile {
            path = "leaf-api/build.gradle.kts"
            outputFile = file("zirkonium-api/build.gradle.kts")
            patchFile = file("zirkonium-api/build.gradle.kts.patch")
        }
        patchRepo("paperApi") {
            upstreamPath = "paper-api"
            patchesDir = file("zirkonium-api/paper-patches")
            outputDir = file("paper-api")
        }
        patchDir("leafApi") {
            upstreamPath = "leaf-api"
            excludes = listOf("build.gradle.kts", "build.gradle.kts.patch", "paper-patches")
            patchesDir = file("zirkonium-api/leaf-patches")
            outputDir = file("leaf-api")
        }
    }
}

val paperMavenPublicUrl = "https://repo.papermc.io/repository/maven-public/"
val leafMavenPublicUrl = "https://maven.nostal.ink/repository/maven-snapshots/"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
        maven(paperMavenPublicUrl)
        maven(leafMavenPublicUrl)
        maven("https://repo.bsdevelopment.org/releases/") // Leaf - Leaf config - ConfigurationMaster-API
    }

    tasks.withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
        options.isFork = true
        options.forkOptions.memoryMaximumSize = "1G"
    }
    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }
    tasks.withType<Test> {
        testLogging {
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
            events(TestLogEvent.STANDARD_OUT)
        }
    }
}
