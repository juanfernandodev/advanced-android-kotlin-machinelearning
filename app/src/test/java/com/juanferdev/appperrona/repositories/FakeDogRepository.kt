package com.juanferdev.appperrona.repositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.models.Dog

class FakeDogRepositorySuccess : DogRepositoryContract {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return ApiResponseStatus.Success(
            listOf(
                Dog(id = 1),
                Dog(id = 2),
                Dog(id = 3)
            )
        )
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return ApiResponseStatus.Success(Unit)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
        return ApiResponseStatus.Success(Dog(id = capturedDogId.toLong()))
    }
}


class FakeDogRepositoryError : DogRepositoryContract {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return ApiResponseStatus.Error(messageId = R.string.unknown_error)
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }
}