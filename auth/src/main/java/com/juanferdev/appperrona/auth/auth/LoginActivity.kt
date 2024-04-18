package com.juanferdev.appperrona.auth.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.auth.auth.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.core.models.User
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

    private fun startMainActivity(user: User) {

        User.setLoggedInUser(this, user)
        try {
            startActivity(
                Intent(
                    this,
                    Class.forName("com.juanferdev.appperrona.camera.main.MainActivity")
                )
            )
        } catch (e: Exception) {
            Toast.makeText(this, "Class not MainActivity not found", Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
