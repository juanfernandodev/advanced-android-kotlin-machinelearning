package com.juanferdev.appperrona.repositories.fakeclassifier

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.juanferdev.appperrona.machinelearning.ClassifierContract
import com.juanferdev.appperrona.machinelearning.DogRecognition
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FakeSuccessClassifier : ClassifierContract {

    private val bitmap = Mockito.mock(Bitmap::class.java)

    override fun recognizeImage(bitmap: Bitmap): List<DogRecognition> {
        return listOf(
            DogRecognition(id = "1", confidence = 0.4F),
            DogRecognition(id = "2", confidence = 0.1F),
            DogRecognition(id = "3", confidence = 0.7F),
            DogRecognition(id = "4", confidence = 0.9F),
            DogRecognition(id = "5", confidence = 0.3F)
        )
    }

    override fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
        return bitmap
    }
}

class FakeErrorClassifier : ClassifierContract {
    override fun recognizeImage(bitmap: Bitmap): List<DogRecognition> = emptyList()

    override fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap? = null
}

@RunWith(MockitoJUnitRunner::class)
class FakeEmptyListDogRecognitionClassifier : ClassifierContract {

    private val bitmap = Mockito.mock(Bitmap::class.java)

    override fun recognizeImage(bitmap: Bitmap): List<DogRecognition> {
        return emptyList()
    }

    override fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
        return bitmap
    }
}