package com.juanferdev.appperrona.core.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.core.dogdetail.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.core.models.User

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                SettingsScreen {
                    logout()
                }
            }
        }
    }

    private fun logout() {
        User.deleteLoggedInUser(this)
        try {
            val intent = Intent(
                this,
                Class.forName("com.juanferdev.appperrona.auth.auth.LoginActivity")
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Class not LoginActivity not found", Toast.LENGTH_LONG).show()
        }


    }
}