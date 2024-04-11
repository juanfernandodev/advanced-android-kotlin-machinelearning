package com.juanferdev.appperrona.machinelearning

import androidx.camera.core.ImageProxy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ClassifierRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val classifier: ClassifierContract
) : ClassifierRepositoryContract {

    override suspend fun recognizedImage(imageProxy: ImageProxy) =
        withContext(dispatcherIO) {
            val bitmap = classifier.convertImageProxyToBitmap(imageProxy)
            if (bitmap == null) {
                DogRecognition()
            } else {
                val listDogRecognition = classifier.recognizeImage(bitmap)
                if (listDogRecognition.isNotEmpty()) {
                    listDogRecognition.first()
                } else {
                    DogRecognition()
                }
            }
        }
}