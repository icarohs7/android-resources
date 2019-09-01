plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    defaults.`android-module`
}

unoxAndroid {
}

dependencies {
    api(project(":resmodules:dialogresources"))

    api(AndroidDeps.materialDialogsBottomSheets)

    AndroidKaptDeps.core.forEach(::kapt)
}
