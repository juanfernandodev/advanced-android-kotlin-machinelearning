package com.juanferdev.appperrona.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.juanferdev.appperrona.R

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        authViewModel.signUp(email, password, passwordConfirmation)
    }
}