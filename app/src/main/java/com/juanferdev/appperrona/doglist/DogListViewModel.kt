package com.juanferdev.appperrona.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DogListViewModel @Inject constructor() : ViewModel() {
    private val dogRepository: DogRepository = DogRepository()
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