package com.juanferdev.appperrona.core.viewmodels.fakerepositories

import com.juanferdev.appperrona.auth.auth.AuthRepositoryContract
import com.juanferdev.appperrona.core.R

class FakeSuccessAuthRepositories : com.juanferdev.appperrona.auth.auth.AuthRepositoryContract {

    private val user = com.juanferdev.appperrona.core.models.User(
        id = 121212L,
        email = "fernaanxd17@gmail.com",
        authenticationToken = "kajdlfjadlfj923j239j"
    )

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(user)
    }

    override suspend fun login(
        email: String,
        password: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(user)
    }
}

class FakeErrorAuthRepositories : com.juanferdev.appperrona.auth.auth.AuthRepositoryContract {
    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(R.string.unknown_error)
    }

    override suspend fun login(
        email: String,
        password: String
    ): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.User> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(R.string.unknown_error)
    }
}