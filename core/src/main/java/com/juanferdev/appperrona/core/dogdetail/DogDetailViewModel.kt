package com.juanferdev.appperrona.core.dogdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.core.api.ApiResponseStatus
import com.juanferdev.appperrona.core.constants.DOG_KEY
import com.juanferdev.appperrona.core.constants.IS_RECOGNITION_KEY
import com.juanferdev.appperrona.core.constants.PROBABLES_DOG_ID_KEY
import com.juanferdev.appperrona.core.doglist.DogRepositoryContract
import com.juanferdev.appperrona.core.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var dog: MutableState<Dog?> = mutableStateOf(
        savedStateHandle[DOG_KEY]
    )
        private set

    private var probableDogsIds: MutableState<List<String>> =
        mutableStateOf(savedStateHandle[PROBABLES_DOG_ID_KEY] ?: emptyList())

    var isRecognition: MutableState<Boolean> =
        mutableStateOf(savedStateHandle[IS_RECOGNITION_KEY] ?: false)
        private set


    val status = mutableStateOf<ApiResponseStatus<Any>?>(null)

    private var _probableDogList =
        MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun getProbableDogs() {
        _probableDogList.value.clear()
        viewModelScope.launch {
            dogRepository.getProbableDogs(probableDogsIds.value)
                .collect { apiResponseStatus ->
                    if (apiResponseStatus is ApiResponseStatus.Success) {
                        val probablesDogsMutableList = _probableDogList.value.toMutableList()
                        val newProbableDog = apiResponseStatus.data
                        probablesDogsMutableList.add(newProbableDog)
                        _probableDogList.value = probablesDogsMutableList
                    }
                }
        }
    }

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