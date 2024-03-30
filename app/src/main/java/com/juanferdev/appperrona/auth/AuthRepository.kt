package com.juanferdev.appperrona.auth

import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.ApiService
import com.juanferdev.appperrona.api.dto.SignInDTO
import com.juanferdev.appperrona.api.dto.SignUpDTO
import com.juanferdev.appperrona.api.dto.UserDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall
import com.juanferdev.appperrona.models.User
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class AuthRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val apiService: ApiService
) : AuthRepositoryContract {

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall(dispatcherIO) {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDTO)
        if (signUpResponse.isSuccess.not()) {
            throw Exception(signUpResponse.message)
        }
        val userDTO = signUpResponse.data.user
        UserDTOMapper().fromUserDTOToUserDomain(userDTO)
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> =
        makeNetworkCall(dispatcherIO) {
            val signInDTO = SignInDTO(email, password)
            val signInResponse = apiService.signIn(signInDTO)
            if (signInResponse.isSuccess.not()) {
                throw Exception(signInResponse.message)
            }
            val userDTO = signInResponse.data.user
            UserDTOMapper().fromUserDTOToUserDomain(userDTO)
        }
}