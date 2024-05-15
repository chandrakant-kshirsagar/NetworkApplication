plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.devrex.sharedtest"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
}

dependencies {
    compileOnly(project(":network"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.junit4)
    implementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.14")
    implementation("androidx.test:core:1.5.0")
    implementation("androidx.arch.core:core-testing:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:1.6.4")
    implementation("org.mockito:mockito-core:3.6.0")
    implementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    api("com.google.truth:truth:1.1.3")
    implementation("app.cash.turbine:turbine:0.12.1")

    implementation(platform(libs.androidx.test.junit4))
    implementation(platform(libs.androidx.test.espresso.core))
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.test.ext.junit)
}