package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall
import com.juanferdev.appperrona.models.Dog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcherIO) {
            val allDogsListResponse = getAllDogs()
            val userDogsListResponse = getUserDogs()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> allDogsListResponse
                userDogsListResponse is ApiResponseStatus.Error -> userDogsListResponse
                allDogsListResponse is ApiResponseStatus.Success &&
                        userDogsListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(
                        getCollectionList(
                            userDogList = userDogsListResponse.data,
                            allDogList = allDogsListResponse.data
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
    ): List<Dog> = allDogList.map {
        if (userDogList.contains(it)) {
            it
        } else {
            Dog(index = it.index)
        }
    }


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


}