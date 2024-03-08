package com.juanferdev.appperrona.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository = DogRepository()) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status


    init {
        downloadDogs()
    }

    @Suppress("UNCHECKED_CAST")
    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.downloadDogs() as ApiResponseStatus<Any>
        }
    }


    fun addDogToUser(dogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.addDogToUser(dogId)
            if (_status.value is ApiResponseStatus.Success) {
                downloadDogs()
            }
        }
    }

}