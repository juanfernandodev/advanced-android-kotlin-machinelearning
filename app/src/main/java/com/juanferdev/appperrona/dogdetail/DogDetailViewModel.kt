package com.juanferdev.appperrona.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel(
    private val dogRepository: DogRepository = DogRepository()
) : ViewModel() {

    val status = mutableStateOf<ApiResponseStatus<Any>?>(null)


    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            status.value = dogRepository.addDogToUser(dogId)
        }
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}