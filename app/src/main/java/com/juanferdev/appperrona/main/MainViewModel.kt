package com.juanferdev.appperrona.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.machinelearning.Classifier
import com.juanferdev.appperrona.machinelearning.ClassifierRepository
import com.juanferdev.appperrona.machinelearning.DogRecognition
import com.juanferdev.appperrona.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import java.nio.MappedByteBuffer
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract
) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _statusDogRecognized = MutableLiveData<DogRecognition>()
    val statusDogRecognized: LiveData<DogRecognition>
        get() = _statusDogRecognized

    private lateinit var classifierRepository: ClassifierRepository

    fun getRecognizedDog(capturedDogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = dogRepository.getRecognizedDog(capturedDogId)
        }
    }

    fun setupClassifier(
        tfLiteModel: MappedByteBuffer,
        labels: List<String>
    ) {
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier = classifier)
    }

    fun recognizedImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _statusDogRecognized.value = classifierRepository.recognizedImage(imageProxy)
            imageProxy.close()
        }
    }
}