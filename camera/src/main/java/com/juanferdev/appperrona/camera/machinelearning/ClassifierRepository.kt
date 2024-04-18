package com.juanferdev.appperrona.camera.machinelearning

import androidx.camera.core.ImageProxy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ClassifierRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val classifier: ClassifierContract
) : ClassifierRepositoryContract {

    override suspend fun recognizedImage(imageProxy: ImageProxy): List<DogRecognition> =
        withContext(dispatcherIO) {
            val bitmap = classifier.convertImageProxyToBitmap(imageProxy)
            if (bitmap == null) {
                listOf(DogRecognition())
            } else {
                val listDogRecognition = classifier.recognizeImage(bitmap)
                if (listDogRecognition.size >= 5) {
                    listDogRecognition.subList(0, 5)
                } else {
                    listDogRecognition
                }
            }
        }
}