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

    api(Deps.okHttp)
    api(Deps.okHttpLoggingInterceptor)

    debugApi(AndroidDeps.chucker)
    releaseApi(AndroidDeps.chuckerNoOp)

    AndroidKaptDeps.core.forEach(::kapt)
}