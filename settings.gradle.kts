include(":core:ui")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Habilita type-safe project accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "LunarAppCompose"
include(":app")
include(":article:article_data")
include(":bookmark:bookmark_data")
include(":article:article_domain")
include(":article:article_presentation")
include(":bookmark:bookmark_presentation")
include(":core")
include(":core:common")
include(":core:ui")
include(":core:data")
