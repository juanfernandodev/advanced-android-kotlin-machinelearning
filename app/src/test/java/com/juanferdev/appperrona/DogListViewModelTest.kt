package com.juanferdev.appperrona

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.dogrepositories.FakeDogRepository
import com.juanferdev.appperrona.dogrepositories.FakeDogRepositoryError
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DogListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun downloadDogListStatusIsSuccess() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListIsNotEmpty() {
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
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