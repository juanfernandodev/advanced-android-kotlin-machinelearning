package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepositoryContract {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>

    suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog>

    fun getProbableDogs(probableDogsIds: List<String>): Flow<ApiResponseStatus<Dog>>
}