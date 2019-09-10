plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("jacoco")
    defaults.`android-module`
}

dependencies {
    api(Deps.kotlinStdLib)
    api(Deps.unoxCoreJvm)
    implementation(Deps.arrowCoreData)
    implementation(Deps.coroutinesCore)
    implementation(Deps.khronos)

    implementation(AndroidDeps.flashbar)
    implementation(AndroidDeps.flexboxLayout)
    implementation(AndroidDeps.fragmentKtx)
    implementation(AndroidDeps.koinAndroid)
    implementation(AndroidDeps.lifecycleRuntimeKtx)
    implementation(AndroidDeps.materialComponents)
    implementation(AndroidDeps.mvRx)
    implementation(AndroidDeps.recyclerView)
    implementation(AndroidDeps.spinKit)
    implementation(AndroidDeps.splittiesAppctx)
    implementation(AndroidDeps.splittiesPermissions)
    implementation(AndroidDeps.splittiesResources)
    implementation(AndroidDeps.splittiesSystemservices)
    implementation(AndroidDeps.splittiesViews)
    implementation(AndroidDeps.stateViews)
    implementation(AndroidDeps.swipeRefreshLayout)
    implementation(AndroidDeps.timber)

    AndroidKaptDeps.core.forEach(::kapt)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            android.sourceSets["main"].java.srcDirs,
            "src/main/kotlin"
    ))
}