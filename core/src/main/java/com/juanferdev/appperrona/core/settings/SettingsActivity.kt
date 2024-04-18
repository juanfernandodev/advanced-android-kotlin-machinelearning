package com.juanferdev.appperrona.core.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juanferdev.appperrona.core.auth.LoginActivity
import com.juanferdev.appperrona.core.dogdetail.ui.theme.AppPerronaTheme

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
        com.juanferdev.appperrona.core.models.User.deleteLoggedInUser(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}