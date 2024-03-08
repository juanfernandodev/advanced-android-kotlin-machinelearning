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
        getDogs()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.getDogCollection() as ApiResponseStatus<Any>
        }
    }


    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.addDogToUser(dogId)
            if (_status.value is ApiResponseStatus.Success) {
                getDogs()
            }
        }
    }

}