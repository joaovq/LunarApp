plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "br.com.joaovq.lunarappcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.com.joaovq.lunarappcompose"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://api.spaceflightnewsapi.net/v4/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"

            resValue("string", "app_name", "Lunar App - DEBUG")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.article.articleData)
    implementation(projects.article.articleDomain)
    implementation(projects.article.articlePresentation)
    implementation(projects.bookmark.bookmarkData)
    implementation(projects.bookmark.bookmarkPresentation)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.timber)

    implementation(libs.bundles.coil)
    implementation(libs.bundles.paging3)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)

    implementation(libs.androidx.core.splashscreen)
    // Kotlin + coroutines
    implementation(libs.bundles.workmanager)
    // When using Kotlin.
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    // mockk
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)

    testImplementation(libs.turbine)

    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.paging.testing)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.androidx.room.testing)
}

kapt {
    correctErrorTypes = true
}