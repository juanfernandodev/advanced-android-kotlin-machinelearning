package com.juanferdev.appperrona.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract
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