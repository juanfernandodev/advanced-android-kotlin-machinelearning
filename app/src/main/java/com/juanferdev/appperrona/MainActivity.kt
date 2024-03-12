package com.juanferdev.appperrona

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.juanferdev.appperrona.api.ApiServiceInterceptor
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivityMainBinding
import com.juanferdev.appperrona.doglist.DogListActivity
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //todo: open camera
            } else {
                Toast.makeText(
                    this,
                    "You need to accept the camera permission to use the camera",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateUser()
        binding = ActivityMainBinding.inflate(layoutInflater)
        initClicks()
        requestCameraPermission()
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
        } else {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //todo: open camera
                }

                shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA
                ) -> {
                    AlertDialog
                        .Builder(this)
                        .setTitle("Accept me please :(")
                        .setMessage("You need to accept the camera permission in order to access to the camera")
                        .setPositiveButton("Aceptar") { _, _ ->
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                        .setNegativeButton("Cancelar") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }

                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            //todo: open camera
        }
    }
}