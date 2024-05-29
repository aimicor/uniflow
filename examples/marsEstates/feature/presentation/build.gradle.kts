plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
}

android {
    namespace = "com.aimicor.uniflow.marsestates.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    implementation(project(":examples:marsEstates:feature:domain"))
    implementation(project(":examples:marsEstates:feature:ui"))

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.navcompose.android)
    implementation(libs.navigation.compose)
    implementation(libs.uniflow.android)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(project(":test"))
}