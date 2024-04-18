package com.juanferdev.appperrona

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.juanferdev.appperrona.composables.constants.SEMANTIC_ERROR_DIALOG
import com.juanferdev.appperrona.composables.constants.SEMANTIC_LOADING_WHEEL
import com.juanferdev.appperrona.doglist.DogListScreen
import com.juanferdev.appperrona.doglist.DogListViewModel
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DogListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockDogRepository = mock(DogRepositoryContract::class.java)

    @Test
    fun progressBarShowsWhenLoadingState(): Unit = runBlocking {
        `when`(mockDogRepository.getDogCollection()).thenReturn(com.juanferdev.appperrona.core.api.ApiResponseStatus.Loading())

        val viewModel = DogListViewModel(
            dogRepository = mockDogRepository
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOADING_WHEEL
        ).assertIsDisplayed()

    }

    @Test
    fun getDogCollectionWhenThereIsAnErrorThenShowErrorDialog(): Unit = runBlocking {
        `when`(mockDogRepository.getDogCollection()).thenReturn(
            com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(
                messageId = R.string.unknown_error
            )
        )
        val viewModel = DogListViewModel(
            dogRepository = mockDogRepository
        )
        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText(
            text = "There was an error"
        ).assertIsDisplayed()

        composeTestRule.onNodeWithTag(SEMANTIC_ERROR_DIALOG).assertIsDisplayed()
    }

    @Test
    fun getDogCollectionWhenIsSuccessThenShowDogsList(): Unit = runBlocking {

        val dogs = listOf(
            com.juanferdev.appperrona.core.models.Dog(
                id = 1L,
                index = 1,
                name = "Mona",
                inCollection = true
            ),
            com.juanferdev.appperrona.core.models.Dog(
                id = 2L,
                index = 2,
                name = "Tarzan",
                inCollection = false
            )
        )

        `when`(mockDogRepository.getDogCollection()).thenReturn(
            com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(
                data = dogs
            )
        )

        val viewModel = DogListViewModel(
            dogRepository = mockDogRepository
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        //composeTestRule.onRoot(useUnmergedTree = true).printToLog("View Tree") <-- To watch all tree view
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-Mona")
            .assertContentDescriptionEquals("Mona").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-2")
            .assertTextEquals("2")
            .assertIsDisplayed() // <-- With text is not need useUnMergedTree, but with Tags it is
    }
}