plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "br.com.joaovq.article_domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.hilt.android)
    implementation(libs.timber)
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.turbine)

    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.paging.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}