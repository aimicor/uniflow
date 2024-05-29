plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget()

    sourceSets {

        commonMain.dependencies {
            implementation(project(":examples:marsEstates:feature:domain"))
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.navcompose.android)
            implementation(libs.uniflow.android)
        }
    }
}

android {
    namespace = "com.aimicor.uniflow.marsestates.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
