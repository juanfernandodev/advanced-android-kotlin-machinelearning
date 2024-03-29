package com.juanferdev.appperrona.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.juanferdev.appperrona.DOG_KEY
import com.juanferdev.appperrona.IS_RECOGNITION_KEY
import com.juanferdev.appperrona.LABEL_PATH
import com.juanferdev.appperrona.MODEL_PATH
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.api.ApiServiceInterceptor
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.databinding.ActivityMainBinding
import com.juanferdev.appperrona.dogdetail.DogDetailActivity
import com.juanferdev.appperrona.doglist.DogListActivity
import com.juanferdev.appperrona.machinelearning.Classifier
import com.juanferdev.appperrona.machinelearning.DogRecognition
import com.juanferdev.appperrona.models.Dog
import com.juanferdev.appperrona.models.User
import com.juanferdev.appperrona.settings.SettingsActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.tensorflow.lite.support.common.FileUtil

class MainActivity : AppCompatActivity() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var classifier: Classifier
    private var isCameraReady = false
    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setUpCamera()
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateUser()
        initClicks()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        requestCameraPermission()
        initObservers()
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

        //ImageAnalysis
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            viewModel.recognizedImage(imageProxy)
        }

        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageCapture,
            imageAnalysis
        )
    }

    private fun enabledTakePhotoButton(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 65.0) {
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getRecognizedDog(dogRecognition.id)
            }
        } else {
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.setupClassifier(
            FileUtil.loadMappedFile(
                this@MainActivity,
                MODEL_PATH
            ),
            FileUtil.loadLabels(
                this@MainActivity,
                LABEL_PATH
            )
        )

    }

    private fun setUpCamera() {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            isCameraReady = true
            startCamera()
        }

    }

    /** THIS CODE TAKE A PHOTO AND SAVE IN THE PHONE, ALSO SHOW THE PHOTO IN A NEW ACTIVITY
     * private fun takePhoto() {
     *
     *         val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
     *         imageCapture.takePicture(outputFileOptions, cameraExecutor,
     *             object : ImageCapture.OnImageSavedCallback {
     *                 override fun onError(error: ImageCaptureException) {
     *                     Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_LONG).show()
     *                 }
     *
     *                 override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
     *                     val photoUri = outputFileResults.savedUri
     *                     photoUri?.let {
     *                         openWholeImageActivity(it)
     *                     }
     *
     *                 }
     *             })
     *     }
     *
     *     private fun openWholeImageActivity(photoUri: Uri) {
     *         val intent = Intent(this, WholeImageActivity::class.java)
     *         intent.putExtra(WholeImageActivity.PHOTO_URI_KEY, photoUri.toString())
     *         startActivity(intent)
     *     }
     *
     *     private fun getOutputPhotoFile(): File {
     *         val mediaDir = externalMediaDirs.firstOrNull()?.let {
     *             File(it, "${resources.getString(R.string.app_name)}.jpg").apply { mkdirs() }
     *         }
     *         return if (mediaDir != null && mediaDir.exists()) {
     *             mediaDir
     *         } else {
     *             filesDir
     *         }
     *     }
     *
     */


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
                    setUpCamera()
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
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            setUpCamera()
        }
    }

    private fun initObservers() {
        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_LONG).show()
                }

                is ApiResponseStatus.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResponseStatus.Success -> {
                    openDetailActivity(status.data)
                }
            }
        }

        viewModel.statusDogRecognized.observe(this) {
            enabledTakePhotoButton(it)
        }
    }

    private fun openDetailActivity(dogRecognized: Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DOG_KEY, dogRecognized)
        intent.putExtra(IS_RECOGNITION_KEY, true)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
    }
}