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

    api(AndroidDeps.lithoCore)
    api(AndroidDeps.lithoWidget)
    api(AndroidDeps.lithoSectionsCore)
    api(AndroidDeps.lithoSectionsWidget)

    AndroidKaptDeps.core.forEach(::kapt)
    kapt(AndroidKaptDeps.litho)
    kapt(AndroidKaptDeps.lithoSections)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            "src/main/kotlin"
    ))
}
