package com.juanferdev.appperrona.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.Dog
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository = DogRepository()) : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            val listDogs = dogRepository.downloadDogs()
            _dogList.postValue(listDogs)
        }
    }

}