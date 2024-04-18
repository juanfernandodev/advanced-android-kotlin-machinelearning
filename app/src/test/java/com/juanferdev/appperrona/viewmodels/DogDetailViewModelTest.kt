package com.juanferdev.appperrona.viewmodels

import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.dogdetail.DogDetailViewModel
import com.juanferdev.appperrona.viewmodels.fakerepositories.FakeErrorDogRepository
import com.juanferdev.appperrona.viewmodels.fakerepositories.FakeSuccessDogRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class DogDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun addDogToUserWhenRepositoryIsSuccessThenStatusIsSuccess() {
        //Give
        val dogDetailViewModel = DogDetailViewModel(dogRepository = FakeSuccessDogRepository())
        //When
        dogDetailViewModel.addDogToUser(dogId = 1L)
        //Then
        val statusValue = dogDetailViewModel.status.value
        assert(statusValue is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success)
    }

    @Test
    fun resetApiResponseStatusWhenRepositoryIsSuccessThenStatusIsNull() {
        //Give
        val dogDetailViewModel = DogDetailViewModel(dogRepository = FakeSuccessDogRepository())
        //When
        dogDetailViewModel.resetApiResponseStatus()
        //Then
        val statusValue = dogDetailViewModel.status.value
        assertNull(statusValue)
    }

    @Test
    fun addDogToUserWhenRepositoryIsErrorThenStatusIsError() {
        //Give
        val dogDetailViewModel = DogDetailViewModel(dogRepository = FakeErrorDogRepository())
        //When
        dogDetailViewModel.addDogToUser(dogId = 1L)
        //Then
        val errorStatus =
            (dogDetailViewModel.status.value as com.juanferdev.appperrona.core.api.ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, errorStatus.messageId)
    }

}