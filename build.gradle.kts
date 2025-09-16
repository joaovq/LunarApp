plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("androidx.room") version libs.versions.room apply false
    alias(libs.plugins.android.library) apply false
    id("app.cash.paparazzi") version "1.3.1" apply false
    id("io.github.takahirom.roborazzi") version "1.43.1" apply false
}