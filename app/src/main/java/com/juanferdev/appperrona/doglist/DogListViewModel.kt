package com.juanferdev.appperrona.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.Dog
import com.juanferdev.appperrona.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository = DogRepository()) : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status


    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            try {
                _status.postValue(ApiResponseStatus.LOADING)
                val listDogs = dogRepository.downloadDogs()
                _dogList.postValue(listDogs)
                _status.postValue((ApiResponseStatus.SUCCESS))
            } catch (e: Exception) {
                _status.postValue((ApiResponseStatus.ERROR))
            }
        }
    }

}