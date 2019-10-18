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
    api(Deps.arrowCoreData)
    api(Deps.coroutinesCore)
    api(Deps.khronos)
    api(Deps.kotlinStdLib)
    api(Deps.unoxCoreJvm)

    api(AndroidDeps.coroutinesAndroid)
    api(AndroidDeps.flashbar)
    api(AndroidDeps.flexboxLayout)
    api(AndroidDeps.fragmentKtx)
    api(AndroidDeps.koinAndroid)
    api(AndroidDeps.lifecycleLivedataKtx)
    api(AndroidDeps.lifecycleRuntimeKtx)
    api(AndroidDeps.materialComponents)
    api(AndroidDeps.mvRx)
    api(AndroidDeps.recyclerView)
    api(AndroidDeps.spinKit)
    api(AndroidDeps.splittiesAppctx)
    api(AndroidDeps.splittiesPermissions)
    api(AndroidDeps.splittiesResources)
    api(AndroidDeps.splittiesSystemservices)
    api(AndroidDeps.splittiesViews)
    api(AndroidDeps.stateViews)
    api(AndroidDeps.swipeRefreshLayout)
    api(AndroidDeps.timber)

    AndroidKaptDeps.core.forEach(::kapt)
}
