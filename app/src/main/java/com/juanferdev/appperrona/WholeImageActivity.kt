package com.juanferdev.appperrona

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.juanferdev.appperrona.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {

    companion object {
        const val PHOTO_URI_KEY = "photo_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY) ?: String()
        val uri = Uri.parse(photoUri)

        uri.path?.let {
            binding.wholeImage.load(File(it))

        } ?: run {
            Toast.makeText(
                this, "Error Showing image no photo uri",
                Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }


    }
}