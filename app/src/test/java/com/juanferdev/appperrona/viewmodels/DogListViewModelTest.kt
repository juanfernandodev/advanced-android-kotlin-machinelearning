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
        val viewModel = DogListViewModel(
            dogRepository = FakeSuccessDogRepository()
        )
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListIsNotEmpty() {
        val viewModel = DogListViewModel(
            dogRepository = FakeSuccessDogRepository()
        )
        val status = viewModel.status.value as ApiResponseStatus.Success
        assert((status.data as List<*>).isNotEmpty())

    }

    @Test
    fun downloadDogListStatusIsError() {
        val viewModel = DogListViewModel(
            dogRepository = FakeErrorDogRepository()
        )
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun downloadDogListMessageIdIsNotZero() {
        val viewModel = DogListViewModel(
            dogRepository = FakeErrorDogRepository()
        )
        val status = viewModel.status.value as ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

}