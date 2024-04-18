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
    buildFeatures {
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    val androidMaterial = "1.11.0"
    implementation("com.google.android.material:material:$androidMaterial")
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
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

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

    //Espresso
    val espresso = "3.5.1"
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")
    implementation("androidx.test.espresso:espresso-idling-resource:$espresso")

    //Coil Images
    val coilVersion = "2.6.0"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")

    //Mockito
    val mockitoVersion = "5.10.0"
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    androidTestImplementation("org.mockito:mockito-android:$mockitoVersion")

    //Test Coroutines
    val kotlinxCoroutines = "1.7.3"
    val testng = "7.3.0"
    val coreTesting = "2.2.0"
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
    testImplementation("org.testng:testng:$testng")
    testImplementation("androidx.arch.core:core-testing:$coreTesting")
}