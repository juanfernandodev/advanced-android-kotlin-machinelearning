package com.juanferdev.appperrona.repositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.ApiService
import com.juanferdev.appperrona.api.dto.SignInDTO
import com.juanferdev.appperrona.api.dto.SignUpDTO
import com.juanferdev.appperrona.api.dto.UserDTO
import com.juanferdev.appperrona.api.responses.AuthApiResponse
import com.juanferdev.appperrona.api.responses.UserResponse
import com.juanferdev.appperrona.auth.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    private val apiServiceMock = Mockito.mock(ApiService::class.java)
    private val idUser = 1L
    private val email = "example@example.com"
    private val password = "12345678"
    private val passwordConfirmation = "12345678"
    private val authenticationToken = "akjdfkajdfkjasdf"

    @Test
    fun signUpWhenAllDataIsCompletedThenStatusIsSuccess(): Unit = runBlocking {
        //Give
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val authApiResponse = AuthApiResponse(
            message = "All look ok",
            isSuccess = true,
            data = UserResponse(
                user = UserDTO(
                    id = idUser,
                    email,
                    authenticationToken
                )
            )
        )
        val authRepository = AuthRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            apiService = apiServiceMock
        )
        `when`(apiServiceMock.signUp(signUpDTO)).thenReturn(authApiResponse)
        //When
        val apiResponseStatus = authRepository.signUp(email, password, passwordConfirmation)
        //Then
        assert(apiResponseStatus is ApiResponseStatus.Success)
    }

    @Test
    fun signUpWhenStatusApiServicesIsNotSuccessThenThrowException(): Unit = runBlocking {
        //Give
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val authApiResponse = AuthApiResponse(
            message = "user_not_found",
            isSuccess = false,
            data = UserResponse(
                user = UserDTO(
                    id = idUser,
                    email,
                    authenticationToken
                )
            )
        )
        `when`(apiServiceMock.signUp(signUpDTO)).thenReturn(authApiResponse)
        val authRepository = AuthRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            apiService = apiServiceMock
        )
        //When
        val apiResponseStatus =
            authRepository.signUp(email, password, passwordConfirmation) as ApiResponseStatus.Error
        //Then
        assertEquals(R.string.user_not_found, apiResponseStatus.messageId)
    }


    @Test
    fun loginWhenAllDataIsCompletedThenStatusIsSuccess(): Unit = runBlocking {
        //Give
        val signInDTO = SignInDTO(email, password)
        val authApiResponse = AuthApiResponse(
            message = "All look good",
            isSuccess = true,
            data = UserResponse(
                user = UserDTO(
                    id = idUser,
                    email,
                    authenticationToken
                )
            )
        )
        `when`(apiServiceMock.signIn(signInDTO)).thenReturn(authApiResponse)
        //When
        val authRepository = AuthRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            apiService = apiServiceMock
        )
        //Then
        val apiResponseStatus = authRepository.login(email, password)
        assert(apiResponseStatus is ApiResponseStatus.Success)
    }

    @Test
    fun loginWhenStatusApiServicesIsNotSuccessThenThrowException(): Unit = runBlocking {
        //Give
        val signInDTO = SignInDTO(email, password)
        val authApiResponse = AuthApiResponse(
            message = "sign_in_error",
            isSuccess = false,
            data = UserResponse(
                user = UserDTO(
                    id = idUser,
                    email,
                    authenticationToken
                )
            )
        )
        `when`(apiServiceMock.signIn(signInDTO)).thenReturn(authApiResponse)
        val authRepository = AuthRepository(
            dispatcherIO = UnconfinedTestDispatcher(),
            apiService = apiServiceMock
        )
        //When
        val apiResponseStatus = authRepository.login(email, password) as ApiResponseStatus.Error
        //Then
        assertEquals(R.string.error_sign_in, apiResponseStatus.messageId)
    }
}