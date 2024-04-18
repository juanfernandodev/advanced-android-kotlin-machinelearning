plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    kotlin("kapt")
}

android {
    namespace = "com.juanferdev.appperrona.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    implementation(project(":core"))

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    //Hilt dependency injection
    val hiltVersion = "2.50"
    val hiltNavigation = "1.2.0"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:${hiltNavigation}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    //Compose
    val activityComposeVersion = "1.9.0"
    val viewModelComposeVersion = "2.7.0"
    val lifecycleRuntimeVersion = "2.7.0"
    val composeBom = platform("androidx.compose:compose-bom:2024.02.02")
    val lifecycleCompose = "2.7.0"
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelComposeVersion")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleCompose")
}