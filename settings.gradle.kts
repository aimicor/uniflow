rootProject.name = "UniFlow"

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":examples:marsEstates:app:hiltApp:composeApp")
include(":examples:marsEstates:app:hiltApp:di")
include(":examples:marsEstates:app:koinApp:composeApp")
include(":examples:marsEstates:app:koinApp:di")
include(":examples:marsEstates:feature:data")
include(":examples:marsEstates:feature:domain")
include(":examples:marsEstates:feature:presentation")
include(":examples:marsEstates:feature:ui")
include(":examples:simpleScaffold")
include(":test")
include(":uniflow")
