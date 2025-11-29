plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Required for Kotlin 2.0+ with Compose
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.achievemeaalk.freedjf"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.achievemeaalk.freedjf"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["ADMOB_APP_ID"] = "ca-app-pub-3940256099942544~3347511713"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    // Configure signing before build types so we can attach it to release if available
    val useReleaseKeystore =
        rootProject.file("release/app-release.jks").exists() && System.getenv("RELEASE_STORE_PASSWORD") != null

    signingConfigs {
        create("release") {
            if (useReleaseKeystore) {
                storeFile = rootProject.file("release/app-release.jks")
                storePassword = System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "ADMOB_BANNER_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "ADMOB_INTERSTITIAL_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/1033173712\"")

            if (useReleaseKeystore) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            buildConfigField("String", "ADMOB_BANNER_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "ADMOB_INTERSTITIAL_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Project module dependency
    implementation(project(":showcase"))

    // Core AndroidX and Material libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.security.crypto)

    // Jetpack Compose dependencies using a bundle for convenience
    implementation(libs.bundles.compose)
    implementation(libs.androidx.constraintlayout.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.biometric)

    // Hilt for Dependency Injection (using KSP)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Room for Local Database (using KSP)
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    implementation(libs.gson) // For Room TypeConverters
    // WorkManager with Hilt integration (using KSP)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.work)
    ksp(libs.hilt.work.compiler)

    // CameraX dependencies using a bundle
    implementation(libs.bundles.camera)

    // ML Kit and required Guava dependency
    implementation(libs.google.mlkit.text.recognition)
    implementation("com.google.android.gms:play-services-ads-lite:24.0.0")
    implementation(libs.google.play.review.ktx)
    // UI and Graphics libraries
    implementation(libs.lottie.compose)
    // Glance for App Widgets using a bundle
    implementation(libs.bundles.glance)
}