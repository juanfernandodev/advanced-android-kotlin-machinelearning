package com.juanferdev.appperrona.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.User
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = authRepository.signUp(email, password, passwordConfirmation)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = authRepository.login(email, password)
        }
    }
}