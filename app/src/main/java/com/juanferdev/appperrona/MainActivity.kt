package com.juanferdev.appperrona

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivityMainBinding
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateUser()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }
        setContentView(binding.root)
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun validateUser() {
        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}