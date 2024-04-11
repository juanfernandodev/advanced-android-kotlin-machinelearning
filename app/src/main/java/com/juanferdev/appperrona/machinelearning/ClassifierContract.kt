package com.juanferdev.appperrona.machinelearning

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy

interface ClassifierContract {
    fun recognizeImage(bitmap: Bitmap): List<DogRecognition>

    fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap?
}