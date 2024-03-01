package com.juanferdev.appperrona.api.responses

import com.juanferdev.appperrona.api.dto.DogDTO

data class DogListResponse(
    val dogs: List<DogDTO>
) {
}