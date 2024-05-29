plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)}

android {
    namespace = "com.aimicor.uniflow.marsestates.hilt"
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
    implementation(project(":examples:marsEstates:feature:data"))
    implementation(project(":examples:marsEstates:feature:domain"))

    implementation(libs.hilt)
    implementation(libs.ktor.contentNegotiation)

    ksp(libs.hilt.compiler)
}