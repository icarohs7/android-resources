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

    api(AndroidDeps.apolloRuntime)
    api(AndroidDeps.apolloCoroutines)

    AndroidKaptDeps.core.forEach(::kapt)
}
