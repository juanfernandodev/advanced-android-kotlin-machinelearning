package com.juanferdev.appperrona.viewmodels

import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.auth.AuthFieldStatus
import com.juanferdev.appperrona.auth.AuthViewModel
import com.juanferdev.appperrona.viewmodels.fakerepositories.FakeErrorAuthRepositories
import com.juanferdev.appperrona.viewmodels.fakerepositories.FakeSuccessAuthRepositories
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun signUpWhenEmailIsNotValidThenMessageIdIsNotValidEmail() {
        //Give
        val email = "ThisIsNotAnEmail"
        val password = "kdjfkajsdkjf"
        val confirmationPassword = "kdjfkajsdkjf"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.signUp(email, password, confirmationPassword)
        //Then
        val authFieldStatus = viewModel.authFieldStatus.value as AuthFieldStatus.Email
        assertEquals(R.string.email_is_not_valid, authFieldStatus.messageId)
    }

    @Test
    fun signUpWhenPasswordIsNotLongerThanEightCharactersThenPasswordFieldIsNotValid() {
        //Give
        val email = "test@example.com"
        val password = "1234567"
        val confirmationPassword = "kdjfkajsdkjf"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.signUp(email, password, confirmationPassword)
        //Then
        val authFieldStatus = viewModel.authFieldStatus.value as AuthFieldStatus.Password
        assertEquals(R.string.length_password_do_not_accepted, authFieldStatus.messageId)
    }

    @Test
    fun signUpWhenConfirmationPasswordDoNotMatchWithPasswordThenConfirmationPasswordFieldIsNotValid() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val confirmationPassword = "abcdefghi"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.signUp(email, password, confirmationPassword)
        //Then
        val authFieldStatus = viewModel.authFieldStatus.value as AuthFieldStatus.ConfirmPassword
        assertEquals(R.string.password_do_not_match, authFieldStatus.messageId)
    }

    @Test
    fun signUpWhenFieldsAreValidThenStatusIsSuccess() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val confirmationPassword = "12345678"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.signUp(email, password, confirmationPassword)
        //Then
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun signUpWhenApiResponseHasErrorThenMessageIdHasAnErrorMessage() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val confirmationPassword = "12345678"
        val viewModel = AuthViewModel(FakeErrorAuthRepositories())
        //When
        viewModel.signUp(email, password, confirmationPassword)
        //Then
        val status = viewModel.status.value as ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

    @Test
    fun loginWhenEmailIsNotValidThenMessageIdIsNotValidEmail() {
        //Given
        val email = "ThisEmailIsNotValid"
        val password = "ksjdfkjdf"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.login(email, password)
        //Then
        val authFieldStatus = viewModel.authFieldStatus.value as AuthFieldStatus.Email
        assertEquals(R.string.email_is_not_valid, authFieldStatus.messageId)
    }

    @Test
    fun loginWhenPasswordLongIsNotValidThenMessageIdPasswordIsNotValid() {
        //Given
        val email = "example@example.com"
        val password = "1234"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.login(email, password)
        //Then
        val authFieldStatus = viewModel.authFieldStatus.value as AuthFieldStatus.Password
        assertEquals(R.string.length_password_do_not_accepted, authFieldStatus.messageId)
    }

    @Test
    fun loginWhenAllFieldsIsValidThenStatusIsSuccess() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.login(email, password)
        //Then
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun loginWhenAllFieldsIsValidThenAuthFieldStatusIsNoError() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.login(email, password)
        //Then
        assert(viewModel.authFieldStatus.value is AuthFieldStatus.NoError)
    }

    @Test
    fun loginWhenApiResponseHasErrorThenMessageIdHasAnErrorMessage() {
        //Give
        val email = "test@example.com"
        val password = "12345678"
        val viewModel = AuthViewModel(FakeErrorAuthRepositories())
        //When
        viewModel.login(email, password)
        //Then
        val status = viewModel.status.value as ApiResponseStatus.Error
        assertNotEquals(0, status.messageId)
    }

    @Test
    fun resetApiResponseStatusWhenAllIsRightThenApiResponseStatusIsNull() {
        //Give
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.resetApiResponseStatus()
        //Then
        assertNull(viewModel.status.value)
    }

    @Test
    fun resetAuthFieldStatusWhenAllIsRightThenAuthFieldStatusIsNoError() {
        //Give
        val viewModel = AuthViewModel(FakeSuccessAuthRepositories())
        //When
        viewModel.resetAuthFieldStatus()
        //Then
        assert(viewModel.authFieldStatus.value is AuthFieldStatus.NoError)
    }
}