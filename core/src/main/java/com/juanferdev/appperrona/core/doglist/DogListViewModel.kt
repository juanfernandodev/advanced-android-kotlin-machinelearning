package com.juanferdev.appperrona.core.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.core.api.ApiResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract
) : ViewModel() {
    val status =
        mutableStateOf<ApiResponseStatus<List<com.juanferdev.appperrona.core.models.Dog>>>(
            com.juanferdev.appperrona.core.api.ApiResponseStatus.Success(
                emptyList()
            )
        )

    init {
        getDogs()
    }

    private fun getDogs() {
        viewModelScope.launch {
            status.value = com.juanferdev.appperrona.core.api.ApiResponseStatus.Loading()
            status.value = dogRepository.getDogCollection()
        }
    }


}