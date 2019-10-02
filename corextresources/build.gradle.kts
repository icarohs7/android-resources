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
    api(res("core"))

    api(Deps.kotlinxSerialization)

    api(AndroidDeps.kotpref)
    api(AndroidDeps.kotprefInitializer)
    api(AndroidDeps.materialEditText)
    api(AndroidDeps.navigationFragmentKtx)
    api(AndroidDeps.navigationUiKtx)
    api(AndroidDeps.stetho)
}
