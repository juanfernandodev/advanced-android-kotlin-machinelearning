package com.juanferdev.appperrona.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.auth.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.main.MainActivity
import com.juanferdev.appperrona.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                AuthScreen { user ->
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

}
