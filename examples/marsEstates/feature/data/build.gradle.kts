plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.android)
        }
        commonMain.dependencies {
            implementation(project(":examples:marsEstates:feature:domain"))
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serializationJson)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.mock)
        }
    }
}

android {
    namespace = "com.aimicor.uniflow.marsestates.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
}