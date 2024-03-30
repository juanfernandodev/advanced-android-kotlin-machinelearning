package com.juanferdev.appperrona.machinelearning

import androidx.camera.core.ImageProxy

fun interface ClassifierRepositoryContract {

    suspend fun recognizedImage(imageProxy: ImageProxy): DogRecognition
}