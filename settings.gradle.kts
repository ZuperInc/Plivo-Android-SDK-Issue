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
        maven(url = "https://jitpack.io")
        maven(url = "https://artifactory.mxtracks.info/artifactory/gradle-dev-local/") {
            content {
                includeModule("OpenCV_all_together_samples","opencv")
            }
        }
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            // Do not change the username below. It should always be "mapbox" (not your username).
            credentials {
                username = "mapbox"
                password = "sk.eyJ1Ijoic2hyaWthbnRocmF2aSIsImEiOiJjbTNkejI2ZDMwODVvMmlxeWQ5d2cwcGlzIn0.tg4uN7oatgyTfwm-hKqXWg"
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

rootProject.name = "Plivo Android Sdk Issue"
include(":app")
 