package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.Dog
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val distpacher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun downloadDogs(): List<Dog> {
        return withContext(distpacher) {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList)
        }
    }
}