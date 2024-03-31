package com.juanferdev.appperrona.repositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.auth.AuthRepositoryContract
import com.juanferdev.appperrona.models.User

class FakeAuthRepositoriesSuccess : AuthRepositoryContract {

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

class FakeAuthRepositoriesError : AuthRepositoryContract {
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