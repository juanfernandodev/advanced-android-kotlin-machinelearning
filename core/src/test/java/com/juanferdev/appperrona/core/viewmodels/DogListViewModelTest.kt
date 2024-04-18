package com.juanferdev.appperrona.core.viewmodels

import com.juanferdev.appperrona.core.MainDispatcherRule
import com.juanferdev.appperrona.core.doglist.DogListViewModel
import com.juanferdev.appperrona.core.viewmodels.fakerepositories.FakeErrorDogRepository
import com.juanferdev.appperrona.core.viewmodels.fakerepositories.FakeSuccessDogRepository
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
        assert(viewModel.status.value is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListIsNotEmpty() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeSuccessDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        val status =
            viewModel.status.value as com.juanferdev.appperrona.core.api.ApiResponseStatus.Success
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
        assert(viewModel.status.value is com.juanferdev.appperrona.core.api.ApiResponseStatus.Error)
    }

    @Test
    fun downloadDogListMessageIdIsNotZero() {
        //Give
        val viewModel = DogListViewModel(
            dogRepository = FakeErrorDogRepository()
        )
        //When: Obtained dog list in ViewModel init method
        //Then
        val status =
            viewModel.status.value as com.juanferdev.appperrona.core.api.ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

}