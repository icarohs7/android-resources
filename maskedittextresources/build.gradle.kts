plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
}

android {
    defaultSettings(project)
}

dependencies {
    api(project(":corelibrary"))

    api(AndroidDeps.maskedEditText)

    AndroidKaptDeps.core.forEach(::kapt)
}