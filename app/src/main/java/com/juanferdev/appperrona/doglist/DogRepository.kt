package com.juanferdev.appperrona.doglist

import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.DogsApi.retrofitService
import com.juanferdev.appperrona.api.dto.DogDTOMapper
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val distpacher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun downloadDogs(): ApiResponseStatus {
        return withContext(distpacher) {
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                ApiResponseStatus.Success(DogDTOMapper().fromDogDTOListToDogDomainList(dogDTOList))
            } catch (e: UnknownHostException) {
                ApiResponseStatus.Error(R.string.error_no_internet)
            } catch (e: Exception) {
                ApiResponseStatus.Error(R.string.error_downloading_data)
            }
        }
    }
}