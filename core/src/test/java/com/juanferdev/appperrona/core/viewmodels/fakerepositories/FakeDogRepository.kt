package com.juanferdev.appperrona.core.viewmodels.fakerepositories

import com.juanferdev.appperrona.core.R
import com.juanferdev.appperrona.core.doglist.DogRepositoryContract
import kotlinx.coroutines.flow.Flow

class FakeSuccessDogRepository : DogRepositoryContract {
    override suspend fun getDogCollection(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(
            listOf(
                com.juanferdev.appperrona.core.models.Dog(id = 1),
                com.juanferdev.appperrona.core.models.Dog(id = 2),
                com.juanferdev.appperrona.core.models.Dog(id = 3)
            )
        )
    }

    override suspend fun addDogToUser(dogId: Long): com.juanferdev.appperrona.core.api.ApiResponseStatus<Any> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(Unit)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(
            com.juanferdev.appperrona.core.models.Dog(
                id = capturedDogId.toLong()
            )
        )
    }

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog>> {
        TODO("Not yet implemented")
    }
}


class FakeErrorDogRepository : DogRepositoryContract {
    override suspend fun getDogCollection(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(messageId = R.string.unknown_error)
    }

    override suspend fun addDogToUser(dogId: Long): com.juanferdev.appperrona.core.api.ApiResponseStatus<Any> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(R.string.unknown_error)
    }

    override suspend fun getRecognizedDog(capturedDogId: String): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog> {
        return com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(R.string.unknown_error)
    }

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog>> {
        TODO("Not yet implemented")
    }
}