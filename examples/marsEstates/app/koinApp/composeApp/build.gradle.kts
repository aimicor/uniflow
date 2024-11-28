plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.aimicor.uniflow.marsestates.koin"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.aimicor.uniflow.marsestates.koin"
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
        sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.android.kotlin.jvm.get()
    }
}

dependencies {
    implementation(project(":examples:marsEstates:app:koinApp:di"))
    implementation(project(":examples:marsEstates:feature:domain"))
    implementation(project(":examples:marsEstates:feature:presentation"))
    implementation(project(":examples:marsEstates:feature:ui"))

    implementation(compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.compose)
    implementation(libs.uniflow.android)
}
