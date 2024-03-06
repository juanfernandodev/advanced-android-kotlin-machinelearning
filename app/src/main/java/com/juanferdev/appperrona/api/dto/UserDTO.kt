package com.juanferdev.appperrona.api.dto

import com.squareup.moshi.Json

data class UserDTO(
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token") val authenticationToken: String
)
