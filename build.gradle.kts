// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"

}
buildscript{
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven ( url = "https://jitpack.io" )
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies{
        classpath (libs.hilt.android.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)

    }

}
