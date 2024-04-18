package com.juanferdev.appperrona.core.doglist

import kotlinx.coroutines.flow.Flow

interface DogRepositoryContract {

    suspend fun getDogCollection(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>>

    suspend fun addDogToUser(dogId: Long): com.juanferdev.appperrona.core.api.ApiResponseStatus<Any>

    suspend fun getRecognizedDog(capturedDogId: String): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog>

    fun getProbableDogs(probableDogsIds: List<String>): Flow<com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog>>
}