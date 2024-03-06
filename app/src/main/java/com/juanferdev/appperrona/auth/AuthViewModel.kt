package com.juanferdev.appperrona.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {
    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch {
            authRepository.signUp(email, password, passwordConfirmation)
        }
    }
}