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
    api(Deps.coroutinesRx2)

    api(AndroidDeps.roomKtx)
    api(AndroidDeps.roomRxJava2)

    AndroidKaptDeps.core.forEach(::kapt)
    AndroidKaptDeps.core.forEach(::kaptTest)
}
