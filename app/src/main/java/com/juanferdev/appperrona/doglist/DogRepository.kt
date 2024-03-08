package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.AddDogToUserDTO
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall
import com.juanferdev.appperrona.models.Dog

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList)
        }

    suspend fun addDogToUser(dogId: String): ApiResponseStatus<Any> =
        makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)
            if (defaultResponse.isSuccess.not()) {
                throw Exception(defaultResponse.message)
            }
        }
}