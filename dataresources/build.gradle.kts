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
    api(project(":corelibrary"))
    api(Deps.coroutinesCore)

    api(AndroidDeps.roomKtx)

    AndroidKaptDeps.core.forEach(::kapt)
    AndroidKaptDeps.core.forEach(::kaptTest)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            "src/main/kotlin"
    ))
}
