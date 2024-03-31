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
class DogListViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract
) : ViewModel() {
    val status =
        mutableStateOf<ApiResponseStatus<List<Dog>>>(ApiResponseStatus.Success(emptyList()))

    init {
        getDogs()
    }

    private fun getDogs() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            status.value = dogRepository.getDogCollection()
        }
    }


}