plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
    defaults.`setup-jacoco`
}

dependencies {
    api(res("corext"))
    api(res("dialog"))

    api(Deps.kotlinReflection)

    api(AndroidDeps.androidxUiFramework)
    api(AndroidDeps.androidxUiLayout)
    api(AndroidDeps.androidxUiMaterial)
}
