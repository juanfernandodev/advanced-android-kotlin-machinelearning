plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.juanferdev.appperrona.core"
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Lifecycle
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    //Hilt dependency injection
    val hiltVersion = "2.50"
    val hiltNavigation = "1.2.0"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:${hiltNavigation}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    // For instrumented tests.
    val hiltAndroidTesting = "2.44"
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltAndroidTesting")
    // ...with Kotlin.
    val hiltAndroidCompiler = "2.50"
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltAndroidCompiler")
    val testRules = "1.5.0"
    implementation("androidx.test:rules:${testRules}")
}