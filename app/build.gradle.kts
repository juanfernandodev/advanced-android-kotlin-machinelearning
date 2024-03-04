plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
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
    implementation("androidx.core:core-ktx:$android_core_version")
    implementation("androidx.appcompat:appcompat:$android_appcompat_version")
    implementation("com.google.android.material:material:$android_material")
    implementation("androidx.constraintlayout:constraintlayout:$constrain_layout_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}