package com.juanferdev.appperrona.api

import com.juanferdev.appperrona.Dog

sealed class ApiResponseStatus {
    class Success(val dogList: List<Dog>) : ApiResponseStatus()
    data object Loading : ApiResponseStatus()
    class Error(val messageId: Int) : ApiResponseStatus()
}