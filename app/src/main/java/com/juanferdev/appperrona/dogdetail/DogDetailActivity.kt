package com.juanferdev.appperrona.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.juanferdev.appperrona.Dog
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.databinding.ActivityDogDetailBinding
import com.juanferdev.appperrona.utils.parcelable

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.parcelable<Dog>(DOG_KEY)
        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }
        binding.dog = dog
        binding.lifeExpectancy.text =
            getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)

    }
}