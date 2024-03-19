package com.juanferdev.appperrona.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.Dog
import kotlinx.coroutines.launch

class DogListViewModel(private val dogRepository: DogRepository = DogRepository()) : ViewModel() {

    val status = mutableStateOf<ApiResponseStatus<Any>>(ApiResponseStatus.Success(emptyList<Dog>()))

    init {
        getDogs()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDogs() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            status.value = dogRepository.getDogCollection() as ApiResponseStatus<Any>
        }
    }


}