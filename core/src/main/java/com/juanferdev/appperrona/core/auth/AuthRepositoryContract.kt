package com.juanferdev.appperrona.core.auth

interface AuthRepositoryContract {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User>

    suspend fun login(
        email: String,
        password: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User>
}