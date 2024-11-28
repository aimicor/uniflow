plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

kotlin {
    jvmToolchain(libs.versions.android.kotlin.jvm.get().toInt())
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
}

dependencies{
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlin.test.junit)
}