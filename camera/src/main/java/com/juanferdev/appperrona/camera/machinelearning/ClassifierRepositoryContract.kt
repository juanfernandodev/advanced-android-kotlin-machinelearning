package com.juanferdev.appperrona.camera.machinelearning

import androidx.camera.core.ImageProxy

fun interface ClassifierRepositoryContract {

    suspend fun recognizedImage(imageProxy: ImageProxy): List<DogRecognition>
}