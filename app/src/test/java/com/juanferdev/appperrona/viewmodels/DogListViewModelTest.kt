package com.juanferdev.appperrona.viewmodels

import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.repositories.FakeDogRepositoryError
import com.juanferdev.appperrona.repositories.FakeDogRepositorySuccess
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class DogListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun downloadDogListStatusIsSuccess() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepositorySuccess()
        )
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListIsNotEmpty() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepositorySuccess()
        )
        val status = viewModel.status.value as ApiResponseStatus.Success
        assert((status.data as List<*>).isNotEmpty())

    }

    @Test
    fun downloadDogListStatusIsError() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepositoryError()
        )
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun downloadDogListMessageIdIsNotZero() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepositoryError()
        )
        val status = viewModel.status.value as ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

}