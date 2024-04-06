package com.juanferdev.appperrona.viewmodels.fakerepositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.auth.AuthRepositoryContract
import com.juanferdev.appperrona.models.User

class FakeSuccessAuthRepositories : AuthRepositoryContract {

    private val user = User(
        id = 121212L,
        email = "fernaanxd17@gmail.com",
        authenticationToken = "kajdlfjadlfj923j239j"
    )

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> {
        return ApiResponseStatus.Success(user)
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
        return ApiResponseStatus.Success(user)
    }
}

class FakeErrorAuthRepositories : AuthRepositoryContract {
    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }
}