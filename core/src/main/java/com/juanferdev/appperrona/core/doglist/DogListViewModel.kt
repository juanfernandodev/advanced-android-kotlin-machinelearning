package com.juanferdev.appperrona.core.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.core.api.ApiResponseStatus
import com.juanferdev.appperrona.core.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract
) : ViewModel() {
    val status =
        mutableStateOf<ApiResponseStatus<List<Dog>>>(
            ApiResponseStatus.Success(
                emptyList()
            )
        )

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