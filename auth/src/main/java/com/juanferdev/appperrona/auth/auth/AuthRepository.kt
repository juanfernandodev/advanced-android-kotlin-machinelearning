package com.juanferdev.appperrona.auth.auth

import com.juanferdev.appperrona.core.api.ApiResponseStatus
import com.juanferdev.appperrona.core.api.dto.SignUpDTO
import com.juanferdev.appperrona.core.api.dto.UserDTOMapper
import com.juanferdev.appperrona.core.api.makeNetworkCall
import com.juanferdev.appperrona.core.models.User
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
    ): ApiResponseStatus<User> =
        makeNetworkCall(dispatcherIO) {
            val signUpDTO = SignUpDTO(
                email,
                password,
                passwordConfirmation
            )
            val signUpResponse = apiService.signUp(signUpDTO)
            if (signUpResponse.isSuccess.not()) {
                throw Exception(signUpResponse.message)
            }
            val userDTO = signUpResponse.data.user
            UserDTOMapper().fromUserDTOToUserDomain(userDTO)
        }

    override suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User> =
        makeNetworkCall(dispatcherIO) {
            val signInDTO = com.juanferdev.appperrona.core.api.dto.SignInDTO(email, password)
            val signInResponse = apiService.signIn(signInDTO)
            if (signInResponse.isSuccess.not()) {
                throw Exception(signInResponse.message)
            }
            val userDTO = signInResponse.data.user
            UserDTOMapper().fromUserDTOToUserDomain(userDTO)
        }
}