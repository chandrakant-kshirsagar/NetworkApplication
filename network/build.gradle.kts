

plugins {
    id("com.android.library")
    id("kotlin-android")

}

android {
    namespace = "com.devrex.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {}
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

     afterEvaluate {
         tasks.getByName("assembleDebug").finalizedBy("copyAARDebug")
         tasks.getByName("assembleRelease").finalizedBy("copyAARRelease")
     }
}
tasks.register("copyAARDebug", Copy::class) {
    from(listOf(buildDir.absolutePath, "outputs", "aar").joinToString(File.separator))
    include("${project.name}-debug.aar")
    into("../app/libs")
}
tasks.register("copyAARRelease", Copy::class) {
    from(listOf(buildDir.absolutePath, "outputs", "aar").joinToString(File.separator))
    include("${project.name}-release.aar")
    into("../app/libs")
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.material3)
    implementation(libs.test.ext.junit)
    testImplementation(libs.junit4)
    testImplementation(libs.junit4.ktx)
    androidTestImplementation(platform(libs.androidx.test.junit4))
    androidTestImplementation(platform(libs.androidx.test.espresso.core))
}
