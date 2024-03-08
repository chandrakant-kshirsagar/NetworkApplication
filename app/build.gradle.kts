import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import groovy.lang.GroovyObject
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig
import org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.jfrog.artifactory")
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
                "String", "APP_KEY", gradleLocalProperties(rootDir).getProperty("APP_KEY")
            )
            buildConfigField(
                "String", "BASE_URL", gradleLocalProperties(rootDir).getProperty("BASE_URL")
            )
        }
        release {
            buildConfigField(
                "String", "APP_KEY", gradleLocalProperties(rootDir).getProperty("APP_KEY")
            )
            buildConfigField(
                "String", "BASE_URL", gradleLocalProperties(rootDir).getProperty("BASE_URL")
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
    repositories {
        google()
        mavenCentral()
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.accompanist.navigation.animation)

    implementation(libs.networksdk)


    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.scalars)
    implementation(libs.converter.gson)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.gson)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.coil.compose)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
val packageName = "com.devrev.network"
val libVersion = "1.0.1"

fun depencyList(): List<Dependency> {
    return arrayListOf(
        libs.retrofit.get(),
        libs.retrofit2.converter.scalars.get(),
        libs.converter.gson.get(),
        libs.okhttp.get(),
        libs.logging.interceptor.get()
    )
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = packageName
            artifactId = "networksdk"
            version = libVersion

            artifact("$rootDir/app/libs/network-release.aar")
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

artifactory {
    setContextUrl("https://kshirsagar.jfrog.io/artifactory")
    publish(delegateClosureOf<PublisherConfig> {
        repository(delegateClosureOf<GroovyObject> {
            setProperty("repoKey", gradleLocalProperties(rootDir).getProperty("repoKey"))
            setProperty("username", gradleLocalProperties(rootDir).getProperty("username"))
            setProperty("password", gradleLocalProperties(rootDir).getProperty("password"))
        })
        defaults(delegateClosureOf<GroovyObject> {

            invokeMethod("publications", "mavenJava")
            setProperty("publishPom", true)
            setProperty("publishIvy", true)
            setProperty("publishArtifacts", true)

        })
    })
    resolve(delegateClosureOf<ResolverConfig> {
        setProperty("repoKey", gradleLocalProperties(rootDir).getProperty("repoKey"))
    })
}
