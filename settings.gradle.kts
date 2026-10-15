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
        maven(url = uri("https://jitpack.io/"))

        // Hosts the Flutter engine artifacts the dashboard needs.
        maven(url = uri("https://storage.googleapis.com/download.flutter.io"))
    }

}

rootProject.name = "OQPAYMobileFinance"
include(":app")
 