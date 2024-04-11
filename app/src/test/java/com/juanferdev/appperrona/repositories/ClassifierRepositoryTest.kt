package com.juanferdev.appperrona.repositories

import com.juanferdev.appperrona.machinelearning.ClassifierRepository
import com.juanferdev.appperrona.repositories.fakeclassifier.FakeEmptyListDogRecognitionClassifier
import com.juanferdev.appperrona.repositories.fakeclassifier.FakeErrorClassifier
import com.juanferdev.appperrona.repositories.fakeclassifier.FakeSuccessClassifier
import com.juanferdev.appperrona.viewmodels.fakeimageproxy.FakeImageProxy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClassifierRepositoryTest {

    private val levelConfidenceZero = 0.0F

    @Test
    fun recognizedImageWhenBitmapIsNotNullThenReturnDogRecognition(): Unit = runBlocking {
        val classifierRepository = ClassifierRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            classifier = FakeSuccessClassifier()
        )

        val dogRecognition = classifierRepository.recognizedImage(FakeImageProxy())
        assert(dogRecognition.confidence != levelConfidenceZero)
    }

    @Test
    fun recognizedImageWhenBitmapIsNullThenReturnDogRecognition(): Unit = runBlocking {
        val classifierRepository = ClassifierRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            classifier = FakeErrorClassifier()
        )

        val dogRecognition = classifierRepository.recognizedImage(FakeImageProxy())
        assert(dogRecognition.confidence == levelConfidenceZero)
    }

    @Test
    fun recognizedImageWhenBitmapIsNotNullAndListIsEmptyThenReturnEmptyDogRecognition(): Unit =
        runBlocking {
            val classifierRepository = ClassifierRepository(
                dispatcherIO = UnconfinedTestDispatcher(),
                classifier = FakeEmptyListDogRecognitionClassifier()
            )

            val dogRecognition = classifierRepository.recognizedImage(FakeImageProxy())
            assert(dogRecognition.confidence == levelConfidenceZero)
        }
}