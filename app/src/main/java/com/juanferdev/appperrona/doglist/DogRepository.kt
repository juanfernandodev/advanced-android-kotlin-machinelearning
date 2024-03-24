package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall
import com.juanferdev.appperrona.models.Dog
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository @Inject constructor() {

    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
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
        makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList)
        }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList)
        }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> =
        makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)
            if (defaultResponse.isSuccess.not()) {
                throw Exception(defaultResponse.message)
            }
        }

    suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> =
        makeNetworkCall {
            val response = retrofitService.getRecognizedDog(capturedDogId)
            if (response.isSuccess.not()) {
                throw Exception(response.message)
            }
            DogDTOMapper().fromDogDTOToDogDomain(response.data.dog)
        }

}