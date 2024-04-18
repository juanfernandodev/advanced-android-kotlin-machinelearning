package com.juanferdev.appperrona.core.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.juanferdev.appperrona.core.MainDispatcherRule
import com.juanferdev.appperrona.core.R
import com.juanferdev.appperrona.core.dogdetail.DogDetailViewModel
import com.juanferdev.appperrona.core.viewmodels.fakerepositories.FakeErrorDogRepository
import com.juanferdev.appperrona.core.viewmodels.fakerepositories.FakeSuccessDogRepository
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
        val dogDetailViewModel = DogDetailViewModel(
            dogRepository = FakeSuccessDogRepository(),
            savedStateHandle = SavedStateHandle()
        )
        //When
        dogDetailViewModel.addDogToUser(dogId = 1L)
        //Then
        val statusValue = dogDetailViewModel.status.value
        assert(statusValue is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success)
    }

    @Test
    fun resetApiResponseStatusWhenRepositoryIsSuccessThenStatusIsNull() {
        //Give
        val dogDetailViewModel = DogDetailViewModel(
            dogRepository = FakeSuccessDogRepository(),
            savedStateHandle = SavedStateHandle()
        )
        //When
        dogDetailViewModel.resetApiResponseStatus()
        //Then
        val statusValue = dogDetailViewModel.status.value
        assertNull(statusValue)
    }

    @Test
    fun addDogToUserWhenRepositoryIsErrorThenStatusIsError() {
        //Give
        val dogDetailViewModel = DogDetailViewModel(
            dogRepository = FakeErrorDogRepository(),
            savedStateHandle = SavedStateHandle()
        )
        //When
        dogDetailViewModel.addDogToUser(dogId = 1L)
        //Then
        val errorStatus =
            (dogDetailViewModel.status.value as com.juanferdev.appperrona.core.api.ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, errorStatus.messageId)
    }

}