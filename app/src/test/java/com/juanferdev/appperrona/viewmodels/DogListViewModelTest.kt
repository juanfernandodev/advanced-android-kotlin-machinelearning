package com.juanferdev.appperrona.viewmodels

import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.repositories.FakeErrorDogRepository
import com.juanferdev.appperrona.repositories.FakeSuccessDogRepository
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class DogListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun downloadDogListStatusIsSuccess() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeSuccessDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListIsNotEmpty() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeSuccessDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        val status = viewModel.status.value as ApiResponseStatus.Success
        assert((status.data as List<*>).isNotEmpty())

    }

    @Test
    fun downloadDogListStatusIsError() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeErrorDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun downloadDogListMessageIdIsNotZero() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeErrorDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        val status = viewModel.status.value as ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

}