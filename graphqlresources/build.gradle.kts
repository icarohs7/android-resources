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
    api(res("okhttp"))

    api(AndroidDeps.apolloRuntime)
    api(AndroidDeps.apolloCoroutines)

    AndroidKaptDeps.core.forEach(::kapt)
}
