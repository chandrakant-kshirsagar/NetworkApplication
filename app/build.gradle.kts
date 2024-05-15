import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.devrex.networkapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devrex.networkapplication"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("../releaseKey")
            storePassword = "Devrex123"
            keyAlias = "dev-key"
            keyPassword = "Devrex123"
        }
        create("release") {
            storeFile = file("../releaseKey")
            storePassword = "Devrex123"
            keyAlias = "dev-key"
            keyPassword = "Devrex123"
        }
    }

    buildTypes {

        debug {
            buildConfigField(
                "String", "APP_KEY", "\"909594533c98883408adef5d56143539\""
            )
            buildConfigField(
                "String", "BASE_URL", "\"https://api.themoviedb.org\""
            )
        }
        release {
            buildConfigField(
                "String", "APP_KEY", "\"909594533c98883408adef5d56143539\""
            )
            buildConfigField(
                "String", "BASE_URL", "\"https://api.themoviedb.org\""
            )

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.navigation.compose)
    // Added Maven repository
    implementation("com.devrex:network-lib:1.0")
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.coil.compose.v260)
    implementation(libs.androidx.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.navigation.compose)
    implementation(libs.gson)
    //TODO  uncomment for testing
/*    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)*/
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.compose.ui.text.googleFonts)

    implementation(libs.androidx.compose.ui.tooling)
    testImplementation(project(":sharedTest"))
    androidTestImplementation(project(":sharedTest"))

    testImplementation(libs.junit4)
    androidTestImplementation(platform(libs.androidx.test.junit4))
    androidTestImplementation(platform(libs.androidx.test.espresso.core))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.test.ext.junit)
}
fun depencyList(): List<Dependency> {
    return arrayListOf(
        libs.retrofit.asProvider().get(),
        libs.retrofit.converter.scalars.get(),
        libs.retrofit.converter.gson.get(),
        libs.okhttp.asProvider().get(),
        libs.okhttp.interceptor.get()
    )
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.devrex"
            artifactId = "network-lib"
            version = "1.0"
            artifact("$rootDir/app/libs/network-release.aar")
            pom {
                pom {
                    withXml {
                        asNode().appendNode("dependencies")?.let { node ->
                            depencyList().forEach {
                                node.appendNode("dependency").apply {
                                    appendNode("groupId", it.group)
                                    appendNode("artifactId", it.name)
                                    appendNode("version", it.version)
                                }
                            }
                        }
                    }
                }
            }
        }


    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/chandrakant-kshirsagar/NetworkApplication")
            credentials {
                val properties = File(rootDir, "local.properties").inputStream().use {
                    Properties().apply { load(it) }
                }
                username =properties.getProperty("username")
                password = properties.getProperty("password")
            }
        }

    }
}
