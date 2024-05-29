plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }
    }
}

android {
    namespace = "com.aimicor.uniflow.marsestates.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
}
