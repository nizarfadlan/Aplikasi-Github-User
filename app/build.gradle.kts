plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.nizarfadlan.aplikasigithubuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nizarfadlan.aplikasigithubuser"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            versionNameSuffix = ".dev"
            isDebuggable = true
        }

        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        animationsDisabled = true
        unitTests.isReturnDefaultValues = true
    }
    dynamicFeatures += setOf(":favorite")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.feature.delivery.ktx)

    // UI
    implementation(libs.constraintlayout)
    implementation(libs.glide)
    implementation(libs.circleimageview)
    implementation(libs.viewpager2)
    implementation(libs.lottie)

    // Navigation
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.dynamic.features.fragment)

    // Testing
    testImplementation(libs.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.idling.resource)
    androidTestImplementation(libs.rules)

    debugImplementation(libs.leakcanary.android)
}

tasks.register("printVersionName") {
    doLast {
        val versionName = android.defaultConfig.versionName
        val buildType = gradle.startParameter.taskNames.find { it.contains("debug") || it.contains("release") } ?: "release"
        val versionNameSuffix = if (buildType.contains("debug")) ".dev" else ""
        println("Version: v$versionName$versionNameSuffix")
    }
}