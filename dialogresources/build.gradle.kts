plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("jacoco")
    defaults.`android-module`
}

unoxAndroid {
}

dependencies {
    api(project(":corelibrary"))

    api(AndroidDeps.materialDialogs)
    api(AndroidDeps.spotsDialog)

    AndroidKaptDeps.core.forEach(::kapt)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            "src/main/kotlin"
    ))
}
