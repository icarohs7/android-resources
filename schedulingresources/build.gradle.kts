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
    compileOnly("javax.annotation:javax.annotation-api:1.3.2") //TODO remove and see what happens
    api(res("corext"))

    implementation(AndroidDeps.timber)
    implementation(AndroidDeps.splittiesAppctx)
    implementation(AndroidDeps.workManagerKtx)

    AndroidKaptDeps.core.forEach(::kapt)
}

setupJacoco {
    sourceDirectories.setFrom(files(
            android.sourceSets["main"].java.srcDirs,
            "src/main/kotlin"
    ))
}