package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.R
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeNetworkCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend (() -> T),
): ApiResponseStatus<T> =
    withContext(dispatcher) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.error_no_internet)
        } catch (e: Exception) {
            val errorMessageId = when (e.message) {
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessageId)
        }

    }