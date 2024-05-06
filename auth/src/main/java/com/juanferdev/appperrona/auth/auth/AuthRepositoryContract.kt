package com.juanferdev.appperrona.auth.auth

import com.juanferdev.appperrona.core.api.ApiResponseStatus
import com.juanferdev.appperrona.core.models.User

interface AuthRepositoryContract {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>

    suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User>
}