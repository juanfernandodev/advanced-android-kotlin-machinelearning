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

        vectorDrawables {
            useSupportLibrary = true
        }
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
    implementation(project(":camera"))
    val lifecycle_version = "2.7.0"
    val activity_version = "1.8.2"
    val android_core_version = "1.12.0"
    val android_appcompat_version = "1.6.1"

    val constrain_layout_version = "2.1.4"
    val navVersion = "2.7.7"


    implementation("androidx.core:core-ktx:$android_core_version")
    implementation("androidx.appcompat:appcompat:$android_appcompat_version")
    implementation("androidx.constraintlayout:constraintlayout:$constrain_layout_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    //Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //Hilt dependency injection
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
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
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.6")

    //CameraX
    val cameraxVersion = "1.4.0-alpha05"
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    //Test Coroutines
    val kotlinxCoroutines = "1.7.3"
    val testng = "7.3.0"
    val coreTesting = "2.2.0"
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
    testImplementation("org.testng:testng:$testng")
    testImplementation("androidx.arch.core:core-testing:$coreTesting")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

