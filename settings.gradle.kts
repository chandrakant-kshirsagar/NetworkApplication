import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
        val properties = File(rootDir, "local.properties").inputStream().use {
            java.util.Properties().apply { load(it) }
        }
        maven {
            credentials {
                username = properties.getProperty("username")
                password = properties.getProperty("password")
            }
            url = URI(properties.getProperty("repoUrl"))
        }
    }

}

rootProject.name = "NetworkApplication"
include(":app")
include(":network")
