plugins {
    id("maven-publish")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.aimicor.uniflow"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.android.kotlin.jvm.get()
    }
    packaging {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }

    dependencies {
        implementation(compose.foundation)
        implementation(libs.androidx.lifecycle.runtime)
        implementation(libs.androidx.lifecycle.viewmodel)

        testImplementation(project(":test"))
        testImplementation(libs.kotlinx.coroutines.test)
        testImplementation(libs.kotlin.test.junit)
        testImplementation(libs.turbine)
    }
}

private val projectDescription = """
    This is an Android ViewModel extension library implementing
    the Uni-Directional Flow arrangement of Model-View-Intent (MVI).
    (https://developer.android.com/jetpack/compose/architecture).
    Standardises ViewModel implementation promoting decoupling, UI
    consistency, state encapsulation and enhanced testability.
  """

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.aimicor"
            artifactId = "uniflow-android"
            version = libs.versions.uniflow.get()

            pom {
                url = "https://github.com/aimicor"
                description = projectDescription
                scm {
                    connection = "scm:git:https://github.com/aimicor/uniflow.git"
                    developerConnection = "scm:git:ssh://github.com:aimicor/uniflow.git"
                    url = "https://github.com/aimicor/uniflow.git"
                }
                licenses {
                    license {
                        name = "The MIT License"
                        url = "https://github.com/aimicor/uniflow/blob/main/LICENSE"
                        distribution = "repo"
                    }
                }
                developers {
                    developer {
                        id = "aimicor"
                        name = "Aimicor"
                        email = "info@aimicor.com"
                        organization = "Aimicor Ltd."
                        organizationUrl = "aimicor.com"
                    }
                }
            }
        }
    }
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}