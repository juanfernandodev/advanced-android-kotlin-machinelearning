plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.juanferdev.appperrona"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.juanferdev.appperrona"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    val lifecycle_version = "2.7.0"
    val activity_version = "1.8.2"
    val retrofit_version = "2.9.0"
    val android_core_version = "1.12.0"
    val android_appcompat_version = "1.6.1"
    val android_material = "1.11.0"
    val constrain_layout_version = "2.1.4"
    val junit_version = "4.13.2"
    val coilVersion = "2.6.0"
    val navVersion = "2.7.7"
    val cameraxVersion = "1.4.0-alpha04"
    val tensorflowLiteVersion = "2.4.0"
    val tensorflowLiteSupportVersion = "0.1.0"
    implementation("androidx.core:core-ktx:$android_core_version")
    implementation("androidx.appcompat:appcompat:$android_appcompat_version")
    implementation("com.google.android.material:material:$android_material")
    implementation("androidx.constraintlayout:constraintlayout:$constrain_layout_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    //Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    //Coil Images
    val coilComposeVersion = "2.6.0"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilComposeVersion")
    //CameraX
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")
    //TensorFlow
    implementation("org.tensorflow:tensorflow-lite:${tensorflowLiteVersion}")
    implementation("org.tensorflow:tensorflow-lite-support:$tensorflowLiteSupportVersion")
    //Compose
    val activityComposeVersion = "1.8.2"
    val viewModelComposeVersion = "2.7.0"
    val lifecycleRuntimeVersion = "2.7.0"
    val composeBom = platform("androidx.compose:compose-bom:2024.02.02")
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
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")

    //Hilt dependency injection
    val hiltVersion = 2.50
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(composeBom)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}