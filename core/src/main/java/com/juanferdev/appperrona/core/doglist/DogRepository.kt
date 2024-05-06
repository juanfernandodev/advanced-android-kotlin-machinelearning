package com.juanferdev.appperrona.core.doglist

import com.juanferdev.appperrona.core.R
import com.juanferdev.appperrona.core.api.ApiResponseStatus
import com.juanferdev.appperrona.core.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.core.api.dto.DogDTOMapper
import com.juanferdev.appperrona.core.api.makeNetworkCall
import com.juanferdev.appperrona.core.models.Dog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val apiService: com.juanferdev.appperrona.core.api.ApiService,
    private val dispatcherIO: CoroutineDispatcher
) : DogRepositoryContract {

    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcherIO) {
            val allDogsListDeferred = async { getAllDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> allDogsListResponse
                userDogsListResponse is ApiResponseStatus.Error -> userDogsListResponse
                allDogsListResponse is ApiResponseStatus.Success &&
                        userDogsListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogsListResponse.data
                        )
                    )
                }

                else -> ApiResponseStatus.Error(R.string.unknown_error)
            }

        }
    }

    private fun getCollectionList(
        allDogList: List<Dog>,
        userDogList: List<Dog>
    ): List<Dog> = allDogList.map { dog ->
        if (userDogList.any { userDog -> userDog.id == dog.id }) {
            dog
        } else {
            Dog(index = dog.index, inCollection = false)
        }
    }.sorted()


    private suspend fun getAllDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall(dispatcherIO) {
            val dogListApiResponse = apiService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper()
                .fromDogDTOListToDogDomainList(dogDTOList)
        }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall(dispatcherIO) {
            val dogListApiResponse = apiService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper()
                .fromDogDTOListToDogDomainList(dogDTOList)
        }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> =
        makeNetworkCall(dispatcherIO) {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = apiService.addDogToUser(addDogToUserDTO)
            if (defaultResponse.isSuccess.not()) {
                throw Exception(defaultResponse.message)
            }
        }

    override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> =
        makeNetworkCall(dispatcherIO) {
            val response = apiService.getRecognizedDog(capturedDogId)
            if (response.isSuccess.not()) {
                throw Exception(response.message)
            }
            DogDTOMapper()
                .fromDogDTOToDogDomain(response.data.dog)
        }

    override fun getProbableDogs(probableDogsIds: List<String>): Flow<ApiResponseStatus<Dog>> =
        flow {
            probableDogsIds.forEach { dogId ->
                emit(getRecognizedDog(dogId))
            }
        }.flowOn(dispatcherIO)

}