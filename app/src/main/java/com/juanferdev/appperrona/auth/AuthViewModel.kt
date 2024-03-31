package com.juanferdev.appperrona.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryContract
) : ViewModel() {
    private val minimumLength = 8

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    var authFieldStatus = mutableStateOf<AuthFieldStatus>(AuthFieldStatus.NoError)
        private set

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {

        when {
            email.isValidEmail().not() -> {
                authFieldStatus.value = AuthFieldStatus.Email(R.string.email_is_not_valid)
            }

            password.length < minimumLength -> {
                authFieldStatus.value =
                    AuthFieldStatus.Password(R.string.length_password_do_not_accepted)
            }

            passwordConfirmation != password -> {
                authFieldStatus.value =
                    AuthFieldStatus.ConfirmPassword(R.string.password_do_not_match)
            }

            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    status.value = authRepository.signUp(email, password, passwordConfirmation)
                }
            }
        }
    }

    fun login(email: String, password: String) {

        when {
            email.isValidEmail().not() -> {
                authFieldStatus.value = AuthFieldStatus.Email(R.string.email_is_not_valid)
            }

            password.length < minimumLength -> {
                authFieldStatus.value =
                    AuthFieldStatus.Password(R.string.length_password_do_not_accepted)
            }

            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    status.value = authRepository.login(email, password)
                }
            }
        }
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

    fun resetAuthFieldStatus() {
        authFieldStatus.value = AuthFieldStatus.NoError
    }
}