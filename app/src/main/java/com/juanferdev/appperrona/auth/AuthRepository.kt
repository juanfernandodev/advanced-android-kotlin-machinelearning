package com.juanferdev.appperrona.auth

import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi
import com.juanferdev.appperrona.api.dto.SignInDTO
import com.juanferdev.appperrona.api.dto.SignUpDTO
import com.juanferdev.appperrona.api.dto.UserDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall
import com.juanferdev.appperrona.models.User

class AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)
        if (signUpResponse.isSuccess.not()) {
            throw Exception(signUpResponse.message)
        }
        val userDTO = signUpResponse.data.user
        UserDTOMapper().fromUserDTOToUserDomain(userDTO)
    }

    suspend fun login(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall {
        val signInDTO = SignInDTO(email, password)
        val signInResponse = DogsApi.retrofitService.signIn(signInDTO)
        if (signInResponse.isSuccess.not()) {
            throw Exception(signInResponse.message)
        }
        val userDTO = signInResponse.data.user
        UserDTOMapper().fromUserDTOToUserDomain(userDTO)
    }
}