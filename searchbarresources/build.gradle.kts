plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
}

dependencies {
    api(res("corext"))

    api(AndroidDeps.materialSearchBar)

    AndroidKaptDeps.core.forEach(::kapt)
}
