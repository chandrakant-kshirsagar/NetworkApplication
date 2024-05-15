import java.io.FileInputStream
import java.net.URI
import java.util.Properties

include(":sharedTest")


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
            Properties().apply { load(it) }
        }
        maven {
            credentials {
                username = properties.getProperty("username")
                password = properties.getProperty("password")
            }
            url = uri("https://maven.pkg.github.com/chandrakant-kshirsagar/NetworkApplication")
        }
    }

}

rootProject.name = "NetworkApplication"
include(":app")
include(":network")
