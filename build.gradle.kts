plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    kotlin("plugin.serialization").version("1.7.10").apply(false)
}

buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("dev.icerock.moko:resources-generator:0.23.0")
    }
}