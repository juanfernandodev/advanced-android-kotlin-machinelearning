package com.juanferdev.appperrona.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.imageproxy.FakeImageProxy
import com.juanferdev.appperrona.main.MainViewModel
import com.juanferdev.appperrona.repositories.FakeErrorDogRepository
import com.juanferdev.appperrona.repositories.FakeSuccessClassifierRepository
import com.juanferdev.appperrona.repositories.FakeSuccessDogRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun getRecognizedDogWhenRepositoriesIsSuccessThenStatusGetDog() {
        //Give
        val mainViewModel = MainViewModel(
            dogRepository = FakeSuccessDogRepository(),
            classifierRepository = FakeSuccessClassifierRepository()
        )
        //When
        mainViewModel.getRecognizedDog("1")
        //Then
        val statusValue = mainViewModel.status.value as ApiResponseStatus.Success
        val dog = statusValue.data
        assertEquals(1L, dog.id)
    }

    @Test
    fun getRecognizedDogWhenDogRepositoryIsWrongThenStatusGetError() {
        //Give
        val mainViewModel = MainViewModel(
            dogRepository = FakeErrorDogRepository(),
            classifierRepository = FakeSuccessClassifierRepository()
        )
        //When
        mainViewModel.getRecognizedDog("1")
        //Then
        val statusValue = mainViewModel.status.value as ApiResponseStatus.Error
        assertEquals(R.string.unknown_error, statusValue.messageId)
    }

    @Test
    fun recognizedImageWhenRepositoriesIsSuccessThenStatusGetDogRecognition() {
        //Give
        val mainViewModel = MainViewModel(
            dogRepository = FakeSuccessDogRepository(),
            classifierRepository = FakeSuccessClassifierRepository()
        )
        val fakeImageProxy = FakeImageProxy()
        //When
        mainViewModel.recognizedImage(fakeImageProxy)
        //Then
        val dogRecognition = mainViewModel.statusDogRecognized.value
        assert(dogRecognition?.id?.isNotEmpty() ?: false)
    }


}