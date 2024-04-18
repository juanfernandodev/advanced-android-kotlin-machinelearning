package com.juanferdev.appperrona.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.auth.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.core.models.User
import com.juanferdev.appperrona.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                AuthScreen { user: User ->
                    startMainActivity(user)
                }
            }
        }
    }

    private fun startMainActivity(user: com.juanferdev.appperrona.core.models.User) {
        com.juanferdev.appperrona.core.models.User.setLoggedInUser(this, user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
