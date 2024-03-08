package com.juanferdev.appperrona

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivityMainBinding
import com.juanferdev.appperrona.doglist.DogListActivity
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateUser()
        binding = ActivityMainBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }
    }

    private fun openSettingsActivity() =
        startActivity(Intent(this, SettingsActivity::class.java))


    private fun openDogListActivity() =
        startActivity(Intent(this, DogListActivity::class.java))

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