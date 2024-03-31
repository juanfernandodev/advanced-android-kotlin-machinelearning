package com.juanferdev.appperrona

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.dogrepositories.FakeDogRepository
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

}