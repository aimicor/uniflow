plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aimicor.uniflow.marsestates.hilt"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.aimicor.uniflow.marsestates.hilt"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
}

dependencies {
    implementation(project(":examples:marsEstates:app:hiltApp:di"))
    implementation(project(":examples:marsEstates:feature:domain"))
    implementation(project(":examples:marsEstates:feature:presentation"))
    implementation(project(":examples:marsEstates:feature:ui"))

    implementation(compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.hilt.navcompose)
    implementation(libs.hilt)
    implementation(libs.uniflow.android)

    ksp(libs.hilt.compiler)
}
