import java.util.Locale

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    jacoco
}

val exclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/Fake*.*"
)

tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

android {
    namespace = "com.juanferdev.appperrona"
    compileSdk = 34


    // Iterate over all application variants (e.g., debug, release)
    applicationVariants.forEach { variant ->
        // Extract variant name and capitalize the first letter
        val variantName = variant.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        // Define task names for unit tests and Android tests
        val unitTests = "test${variantName}UnitTest"
        val androidTests = "connected${variantName}AndroidTest"

        // Register a JacocoReport task for code coverage analysis
        tasks.register<JacocoReport>("Jacoco${variantName}CodeCoverage") {
            // Depend on unit tests and Android tests tasks
            dependsOn(listOf(unitTests, androidTests))
            // Set task grouping and description
            group = "Reporting"
            description = "Execute UI and unit tests, generate and combine Jacoco coverage report"
            // Configure reports to generate both XML and HTML formats
            reports {
                xml.required.set(true)
                html.required.set(true)
            }
            // Set source directories to the main source directory
            sourceDirectories.setFrom(layout.projectDirectory.dir("src/main"))
            // Set class directories to compiled Java and Kotlin classes, excluding specified exclusions
            classDirectories.setFrom(files(
                fileTree(layout.buildDirectory.dir("intermediates/javac/")) {
                    exclude(exclusions)
                },
                fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/")) {
                    exclude(exclusions)
                }
            ))
            // Collect execution data from .exec and .ec files generated during test execution
            executionData.setFrom(files(
                fileTree(layout.buildDirectory) { include(listOf("**/*.exec", "**/*.ec")) }
            ))
        }
    }

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
        // Replace com.example.android.dagger with your class path.
        testInstrumentationRunner = "com.juanferdev.appperrona.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
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
    implementation(project(":core"))
    val lifecycle_version = "2.7.0"
    val activity_version = "1.8.2"
    val android_core_version = "1.12.0"
    val android_appcompat_version = "1.6.1"
    val android_material = "1.11.0"
    val constrain_layout_version = "2.1.4"
    val junit_version = "4.13.2"
    val coilVersion = "2.6.0"
    val navVersion = "2.7.7"
    val cameraxVersion = "1.4.0-alpha04"

    implementation("androidx.core:core-ktx:$android_core_version")
    implementation("androidx.appcompat:appcompat:$android_appcompat_version")
    implementation("com.google.android.material:material:$android_material")
    implementation("androidx.constraintlayout:constraintlayout:$constrain_layout_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    //Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    //Coil Images
    val coilComposeVersion = "2.6.0"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilComposeVersion")
    //CameraX
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")
    //Composef
    val activityComposeVersion = "1.8.2"
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
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleCompose")

    //TensorFlow
    val tensorflowLiteVersion = "2.4.0"
    val tensorflowLiteSupportVersion = "0.1.0"
    implementation("org.tensorflow:tensorflow-lite:${tensorflowLiteVersion}")
    implementation("org.tensorflow:tensorflow-lite-support:$tensorflowLiteSupportVersion")

    //Hilt dependency injection
    val hiltVersion = "2.50"
    val hiltNavigation = "1.2.0"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:${hiltNavigation}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(composeBom)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")


    //Test Coroutines
    val kotlinxCoroutines = "1.7.3"
    val testng = "7.3.0"
    val coreTesting = "2.2.0"
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
    testImplementation("org.testng:testng:$testng")
    testImplementation("androidx.arch.core:core-testing:$coreTesting")

    //Mockito
    val mockitoVersion = "5.10.0"
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    androidTestImplementation("org.mockito:mockito-android:$mockitoVersion")

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
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

