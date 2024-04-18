package com.juanferdev.appperrona.core.api.responses

import com.squareup.moshi.Json

class DogListApiResponse(
    val message: String,
    @field:Json(name = "is_sucess") val isSuccess: Boolean,
    val data: DogListResponse
) {
}