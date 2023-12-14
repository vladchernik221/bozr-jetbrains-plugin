fun properties(key: String, defaultValue: String = ""): String =
        project.findProperty(key)?.toString() ?: defaultValue

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

val since = properties("sinceIdeaBuild")

repositories {
    mavenCentral()
}

idea {
    module {
        isDownloadSources = true
    }
}

intellij {
    updateSinceUntilBuild.set(false)
    type.set(properties("platformType"))
    version.set(properties("platformVersion"))
    plugins.set(
            properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty)
    )
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set(since)
    }
}
