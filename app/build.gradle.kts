plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id("androidx.room")
}

room {
    schemaDirectory("debug", "$projectDir/schemas/debug")
    // Applies to variants that aren't matched by other configurations.
    schemaDirectory("$projectDir/schemas")
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
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":article:article_data"))
    implementation(project(":article:article_domain"))
    implementation(project(":article:article_presentation"))
    implementation(project(":bookmark:bookmark_data"))
    implementation(project(":bookmark:bookmark_presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.timber)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.hilt.work)
    // When using Kotlin.
    kapt(libs.androidx.hilt.compiler)

    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.room.compiler)
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