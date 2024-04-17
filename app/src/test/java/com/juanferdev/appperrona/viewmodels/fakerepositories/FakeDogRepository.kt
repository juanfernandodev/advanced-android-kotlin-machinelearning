package com.juanferdev.appperrona.viewmodels.fakerepositories

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.models.Dog
import kotlinx.coroutines.flow.Flow

class FakeSuccessDogRepository : DogRepositoryContract {
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

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<ApiResponseStatus<Dog>> {
        TODO("Not yet implemented")
    }
}


class FakeErrorDogRepository : DogRepositoryContract {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return ApiResponseStatus.Error(messageId = R.string.unknown_error)
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
        return ApiResponseStatus.Error(R.string.unknown_error)
    }

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<ApiResponseStatus<Dog>> {
        TODO("Not yet implemented")
    }
}