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
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.juanferdev.appperrona.api.ApiServiceInterceptor
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivityMainBinding
import com.juanferdev.appperrona.doglist.DogListActivity
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
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
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        validateUser()
        binding = ActivityMainBinding.inflate(layoutInflater)
        initClicks()
        requestCameraPermission()
        setContentView(binding.root)
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

        var camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
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
                    startCamera()
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
            startCamera()
        }
    }
}