plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
}

android {
    defaultSettings()
}

dependencies {
    api(project(":corelibrary"))

    AndroidKaptDeps.core.forEach(::kapt)
}