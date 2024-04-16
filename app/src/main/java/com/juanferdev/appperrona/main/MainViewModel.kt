package com.juanferdev.appperrona.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.machinelearning.ClassifierRepositoryContract
import com.juanferdev.appperrona.machinelearning.DogRecognition
import com.juanferdev.appperrona.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract,
    private val classifierRepository: ClassifierRepositoryContract
) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _statusDogRecognized = MutableLiveData<DogRecognition>()
    val statusDogRecognized: LiveData<DogRecognition>
        get() = _statusDogRecognized

    val probableDogIds = ArrayList<String>()

    fun getRecognizedDog(capturedDogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.getRecognizedDog(capturedDogId)
        }
    }

    fun recognizedImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            val listDogRecognition = classifierRepository.recognizedImage(imageProxy)
            updateDogRecognition(listDogRecognition)
            updateProbableDogIds(listDogRecognition)
            imageProxy.close()
        }
    }

    private fun updateDogRecognition(dogRecognitionList: List<DogRecognition>) {
        _statusDogRecognized.value = dogRecognitionList.first()
    }

    private fun updateProbableDogIds(dogRecognitionList: List<DogRecognition>) {
        probableDogIds.clear()
        val recognitionListId = if (dogRecognitionList.size >= 5) {
            dogRecognitionList.subList(1, 5).map {
                it.id
            }
        } else {
            listOf()
        }
        probableDogIds.addAll(recognitionListId)
    }
}