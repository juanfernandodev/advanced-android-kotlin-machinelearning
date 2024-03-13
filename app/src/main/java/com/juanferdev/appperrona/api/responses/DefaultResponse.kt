package com.juanferdev.appperrona.api.responses

import com.squareup.moshi.Json

data class DefaultResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean
)
