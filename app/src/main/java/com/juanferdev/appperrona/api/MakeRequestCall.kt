package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.R
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeNetworkCall(
    call: suspend (() -> T),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): ApiResponseStatus<T> =
    withContext(dispatcher) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.error_no_internet)
        } catch (e: Exception) {
            ApiResponseStatus.Error(R.string.error_downloading_data)
        }


    }