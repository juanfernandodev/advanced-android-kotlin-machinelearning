package com.juanferdev.appperrona.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository = DogRepository()) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status


    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading
            _status.value = dogRepository.downloadDogs()
        }
    }

}