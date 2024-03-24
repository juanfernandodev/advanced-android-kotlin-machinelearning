package com.juanferdev.appperrona.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.juanferdev.appperrona.auth.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.main.MainActivity
import com.juanferdev.appperrona.models.User

class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                AuthScreen(
                    onLoginButtonClick = ::onLoginButtonClick,
                    onSignUpButtonClick = ::onSignUpButtonClick,
                    onResetApiResponse = ::resetApiResponseStatus,
                    viewModel = viewModel
                ) { user ->
                    startMainActivity(user)
                }
            }
        }
    }

    private fun startMainActivity(user: User) {
        User.setLoggedInUser(this, user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onLoginButtonClick(email: String, password: String) {
        viewModel.login(email, password)
    }

    private fun onSignUpButtonClick(email: String, password: String, passwordConfirmation: String) {
        viewModel.signUp(email, password, passwordConfirmation)
    }
}
