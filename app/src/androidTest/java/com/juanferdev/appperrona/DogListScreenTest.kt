package com.juanferdev.appperrona

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.composables.constants.SEMANTIC_LOADING_WHEEL
import com.juanferdev.appperrona.doglist.DogListScreen
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.models.Dog
import org.junit.Rule
import org.junit.Test

class DogListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progressBarShowsWhenLoadingState() {
        class FakeDogRepository : DogRepositoryContract {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val fakeViewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = fakeViewModel
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOADING_WHEEL
        ).assertIsDisplayed()

    }
}