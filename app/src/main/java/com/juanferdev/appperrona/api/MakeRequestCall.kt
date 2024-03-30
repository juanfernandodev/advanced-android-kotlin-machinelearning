package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.R
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    call: suspend (() -> T),
): ApiResponseStatus<T> =
    withContext(dispatcherIO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.error_no_internet)
        } catch (e: HttpException) {
            val errorMessageId = when (e.code()) {
                UNAUTHORIZED_ERROR_CODE -> R.string.wrong_user_or_password
                else -> R.string.error_network
            }
            ApiResponseStatus.Error(errorMessageId)
        } catch (e: Exception) {
            val errorMessageId = when (e.message) {
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                "user_not_found" -> R.string.user_not_found
                "error_adding_dog" -> R.string.error_adding_dog
                "Dog already belongs to user" -> R.string.dog_already_belongs_to_user
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessageId)
        }

    }