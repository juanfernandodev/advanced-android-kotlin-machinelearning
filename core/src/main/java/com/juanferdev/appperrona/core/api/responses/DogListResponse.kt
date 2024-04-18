package com.juanferdev.appperrona.core.api.responses

import com.juanferdev.appperrona.core.api.dto.DogDTO

data class DogListResponse(
    val dogs: List<DogDTO>
)