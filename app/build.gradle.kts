import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("org.jlleitschuh.gradle.ktlint")
    id("kotlin-parcelize")

    alias(libs.plugins.hilt)
    kotlin("kapt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    val localProperties =
        Properties().apply {
            val file = rootProject.file("local.properties")
            if (file.exists()) {
                load(file.inputStream())
            }
        }

    namespace = "com.arttrip.app"
    compileSdk {
        version = release(36)
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties["RELEASE_STORE_FILE"] as String)
            storePassword = localProperties["RELEASE_STORE_PASSWORD"] as String
            keyAlias = localProperties["RELEASE_KEY_ALIAS"] as String
            keyPassword = localProperties["RELEASE_KEY_PASSWORD"] as String
        }
    }

    defaultConfig {
        applicationId = "com.arttrip.app"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val kakaoNativeAppKey = localProperties.getProperty("KAKAO_NATIVE_APP_KEY") ?: ""
        val googleWebClientId = localProperties.getProperty("GOOGLE_WEB_CLIENT_ID") ?: ""

        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: ""

        manifestPlaceholders["kakaoNativeAppKey"] = kakaoNativeAppKey
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            "\"$kakaoNativeAppKey\"",
        )
        buildConfigField(
            "String",
            "GOOGLE_WEB_CLIENT_ID",
            "\"$googleWebClientId\"",
        )
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.exifinterface)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Retrofit2
    implementation(libs.retrofit)

    // Gson
    implementation(libs.retrofit.converter.gson)

    // OkHttp3
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kakao.sdk.user)

    implementation(libs.security.crypto)
    implementation(libs.androidx.datastore.preferences)
    // Coil
    implementation(libs.coil.compose)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.google.maps)
    implementation(libs.google.location)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
}
