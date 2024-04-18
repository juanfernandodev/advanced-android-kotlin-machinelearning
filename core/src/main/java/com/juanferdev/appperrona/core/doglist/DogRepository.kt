package com.juanferdev.appperrona.core.doglist

import com.juanferdev.appperrona.core.R
import com.juanferdev.appperrona.core.api.dto.DogDTOMapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DogRepository @Inject constructor(
    private val apiService: com.juanferdev.appperrona.core.api.ApiService,
    private val dispatcherIO: CoroutineDispatcher
) : DogRepositoryContract {

    override suspend fun getDogCollection(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>> {
        return withContext(dispatcherIO) {
            val allDogsListDeferred = async { getAllDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is com.juanferdev.appperrona.core.api.ApiResponseStatus.Error -> allDogsListResponse
                userDogsListResponse is com.juanferdev.appperrona.core.api.ApiResponseStatus.Error -> userDogsListResponse
                allDogsListResponse is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success &&
                        userDogsListResponse is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success -> {
                    com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogsListResponse.data
                        )
                    )
                }

                else -> com.juanferdev.appperrona.core.api.ApiResponseStatus.Error(R.string.unknown_error)
            }

        }
    }

    private fun getCollectionList(
        allDogList: List<com.juanferdev.appperrona.core.models.Dog>,
        userDogList: List<com.juanferdev.appperrona.core.models.Dog>
    ): List<com.juanferdev.appperrona.core.models.Dog> = allDogList.map { dog ->
        if (userDogList.any { userDog -> userDog.id == dog.id }) {
            dog
        } else {
            com.juanferdev.appperrona.core.models.Dog(index = dog.index, inCollection = false)
        }
    }.sorted()


    private suspend fun getAllDogs(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val dogListApiResponse = apiService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            com.juanferdev.appperrona.core.api.dto.DogDTOMapper()
                .fromDogDTOListToDogDomainList(dogDTOList)
        }

    private suspend fun getUserDogs(): com.juanferdev.appperrona.core.api.ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val dogListApiResponse = apiService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            com.juanferdev.appperrona.core.api.dto.DogDTOMapper()
                .fromDogDTOListToDogDomainList(dogDTOList)
        }

    override suspend fun addDogToUser(dogId: Long): com.juanferdev.appperrona.core.api.ApiResponseStatus<Any> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val addDogToUserDTO = com.juanferdev.appperrona.core.api.dto.AddDogToUserDTO(dogId)
            val defaultResponse = apiService.addDogToUser(addDogToUserDTO)
            if (defaultResponse.isSuccess.not()) {
                throw Exception(defaultResponse.message)
            }
        }

    override suspend fun getRecognizedDog(capturedDogId: String): com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog> =
        com.juanferdev.appperrona.core.api.makeNetworkCall(dispatcherIO) {
            val response = apiService.getRecognizedDog(capturedDogId)
            if (response.isSuccess.not()) {
                throw Exception(response.message)
            }
            DogDTOMapper()
                .fromDogDTOToDogDomain(response.data.dog)
        }

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<com.juanferdev.appperrona.core.api.ApiResponseStatus<com.juanferdev.appperrona.core.models.Dog>> =
        flow {
            probableDogsIds.forEach { dogId ->
                emit(getRecognizedDog(dogId))
            }
        }.flowOn(dispatcherIO)

}