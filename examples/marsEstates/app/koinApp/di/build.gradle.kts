plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(project(":examples:marsEstates:feature:data"))
            implementation(project(":examples:marsEstates:feature:domain"))
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.koin.core)
        }
    }
}


android {
    namespace = "com.aimicor.uniflow.marsestates.koin"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
}