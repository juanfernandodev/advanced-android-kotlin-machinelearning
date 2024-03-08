package com.juanferdev.appperrona.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivitySettingsBinding
import com.juanferdev.appperrona.models.User

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        binding.logoutButton.setOnClickListener {
            logout()
        }
        setContentView(binding.root)
    }

    private fun logout() {
        User.deleteLoggedInUser(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}