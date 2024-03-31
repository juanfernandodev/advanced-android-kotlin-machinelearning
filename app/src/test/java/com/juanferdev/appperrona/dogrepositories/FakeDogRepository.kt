package com.juanferdev.appperrona.dogrepositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.models.Dog

class FakeDogRepository : DogRepositoryContract {
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
        return ApiResponseStatus.Success(Dog())
    }
}


class FakeDogRepositoryError : DogRepositoryContract {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return ApiResponseStatus.Error(messageId = R.string.unknown_error)
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return ApiResponseStatus.Success(Unit)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
        return ApiResponseStatus.Success(Dog())
    }
}