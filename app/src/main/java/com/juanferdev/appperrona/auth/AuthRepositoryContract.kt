package com.juanferdev.appperrona.auth

import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.User

interface AuthRepositoryContract {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>

    suspend fun login(email: String, password: String): ApiResponseStatus<User>
}