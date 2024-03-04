package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.Dog
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import com.juanferdev.appperrona.api.makeNetworkCall

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList)
        }
}