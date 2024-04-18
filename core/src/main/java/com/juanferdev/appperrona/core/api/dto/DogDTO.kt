package com.juanferdev.appperrona.core.api.dto

import com.squareup.moshi.Json

data class DogDTO(
    val id: Long,
    val index: Int,
    @field:Json(name = "name_en") val name: String = String(),
    @field:Json(name = "dog_type") val type: String = String(),
    @field:Json(name = "height_female") val heightFemale: Double = 0.0,
    @field:Json(name = "height_male") val heightMale: Double = 0.0,
    @field:Json(name = "image_url") val imageUrl: String = String(),
    @field:Json(name = "life_expectancy") val lifeExpectancy: String = String(),
    val temperament: String = String(),
    @field:Json(name = "weight_female") val weightFemale: String = String(),
    @field:Json(name = "weight_male") val weightMale: String = String()
)