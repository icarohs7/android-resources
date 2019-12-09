plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    defaults.`android-module`
    defaults.`setup-jacoco`
}

dependencies {
    api(res("corext"))
    api(res("dialog"))

    api(AndroidDeps.androidxUiFoundation)
    api(AndroidDeps.androidxUiLayout)
    api(AndroidDeps.androidxUiMaterial)
    api(AndroidDeps.androidxUiTooling)
}
