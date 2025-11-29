// This file configures project-level settings, including plugin repositories
// and the modules to be included in the build.

// The pluginManagement block configures the repositories for Gradle plugins.
// It is the modern replacement for the buildscript { repositories {} } block.
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// The dependencyResolutionManagement block configures repositories for project dependencies.
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ModernizedAndroidProject"
include(":app")
include(":showcase")
