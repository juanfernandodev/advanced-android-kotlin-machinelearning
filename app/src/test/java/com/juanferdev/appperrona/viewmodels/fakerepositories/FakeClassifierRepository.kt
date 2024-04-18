package com.juanferdev.appperrona.viewmodels.fakerepositories

import androidx.camera.core.ImageProxy
import com.juanferdev.appperrona.camera.machinelearning.ClassifierRepositoryContract
import com.juanferdev.appperrona.camera.machinelearning.DogRecognition

class FakeSuccessClassifierRepository : ClassifierRepositoryContract {
    override suspend fun recognizedImage(imageProxy: ImageProxy): List<DogRecognition> =
        listOf(DogRecognition(id = "1", confidence = 0.7F))
}