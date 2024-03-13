plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

dependencies{
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlin.test.junit)
}