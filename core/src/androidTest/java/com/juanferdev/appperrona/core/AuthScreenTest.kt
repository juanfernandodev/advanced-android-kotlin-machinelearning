package com.juanferdev.appperrona.core

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.juanferdev.appperrona.core.auth.AuthRepositoryContract
import com.juanferdev.appperrona.core.auth.AuthScreen
import com.juanferdev.appperrona.core.auth.AuthViewModel
import com.juanferdev.appperrona.core.constants.SemanticConstants
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_EMAIL_FIELD
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_ERROR_TEXT_PASSWORD_FIELD
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_LOGIN_BUTTON
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_LOGIN_SCREEN_REGISTER_BUTTON
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_PASSWORD_FIELD
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_SIGN_UP_BUTTON
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockAuthRepository = Mockito.mock(AuthRepositoryContract::class.java)

    @Test
    fun onClickRegisterWhenAllIsGoodThenOpenSignUpScreen() {
        val viewModel = AuthViewModel(
            authRepository = mockAuthRepository
        )

        composeTestRule.setContent {
            AuthScreen(
                viewModel = viewModel,
                onUserLogin = {}
            )
        }

        //Make sure that the app is on the LoginScreen
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_BUTTON
        ).assertIsDisplayed()

        //On click in Register Button of LogicScreen
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_SCREEN_REGISTER_BUTTON
        ).performClick()

        //Sign up button of LoginScreen is displayed
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_SIGN_UP_BUTTON
        ).assertIsDisplayed()
    }

    @Test
    fun onClickLoginButtonWhenEmailIsInvalidThenShowErrorMessageOnEmailField() {
        val viewModel = AuthViewModel(
            authRepository = mockAuthRepository
        )

        composeTestRule.setContent {
            AuthScreen(
                viewModel = viewModel,
                onUserLogin = {}
            )
        }

        //On click in login button with empty email
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_BUTTON
        ).performClick()

        //Make sure that the text email field has error message
        composeTestRule
            .onNodeWithTag(
                testTag = SemanticConstants.SEMANTIC_ERROR_TEXT_EMAIL_FIELD
            )
            .assertTextEquals(
                "Email is not valid"
            )

        //Fill email field with a invalid email
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_EMAIL_FIELD
        ).performTextInput(
            text = "ThisIsNotAnEmail"
        )

        //On click in login button with invalid email
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_BUTTON
        ).performClick()

        //Make sure that the text email field has error message
        composeTestRule
            .onNodeWithTag(
                testTag = SemanticConstants.SEMANTIC_ERROR_TEXT_EMAIL_FIELD
            )
            .assertTextEquals(
                "Email is not valid"
            )
    }

    @Test
    fun onClickLoginButtonWhenPasswordIsInvalidThenShowErrorMessageOnPasswordField() {
        val viewModel = AuthViewModel(
            authRepository = mockAuthRepository
        )
        composeTestRule.setContent {
            AuthScreen(
                viewModel = viewModel,
                onUserLogin = {}
            )
        }

        //To fill email field with a valid email
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_EMAIL_FIELD
        ).performTextInput(
            text = "ThisIsAnEmail@email.com"
        )

        //To fill password field with a invalid/empty password
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_PASSWORD_FIELD
        ).performTextInput(
            text = String()
        )

        //On Click in login button
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_BUTTON
        ).performClick()

        //Make sure that the password field has an error message
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_ERROR_TEXT_PASSWORD_FIELD
        ).assertTextEquals(
            "The password must have a minimum of 8 characters"
        )

    }
}