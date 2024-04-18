package com.juanferdev.appperrona.auth

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class AuthRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val apiService: com.juanferdev.appperrona.core.api.ApiService
) : AuthRepositoryContract {

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val signUpDTO = com.juanferdev.appperrona.core.api.dto.SignUpDTO(
                email,
                password,
                passwordConfirmation
            )
            val signUpResponse = apiService.signUp(signUpDTO)
            if (signUpResponse.isSuccess.not()) {
                throw Exception(signUpResponse.message)
            }
            val userDTO = signUpResponse.data.user
            com.juanferdev.appperrona.core.api.dto.UserDTOMapper().fromUserDTOToUserDomain(userDTO)
        }

    override suspend fun login(
        email: String,
        password: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val signInDTO = com.juanferdev.appperrona.core.api.dto.SignInDTO(email, password)
            val signInResponse = apiService.signIn(signInDTO)
            if (signInResponse.isSuccess.not()) {
                throw Exception(signInResponse.message)
            }
            val userDTO = signInResponse.data.user
            com.juanferdev.appperrona.core.api.dto.UserDTOMapper().fromUserDTOToUserDomain(userDTO)
        }
}