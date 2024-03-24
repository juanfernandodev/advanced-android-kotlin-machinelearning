package com.juanferdev.appperrona.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.juanferdev.appperrona.DOG_KEY
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.composables.ErrorDialog
import com.juanferdev.appperrona.composables.LoadingWheel
import com.juanferdev.appperrona.dogdetail.DogDetailActivity
import com.juanferdev.appperrona.dogdetail.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.models.Dog

class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                when (val status = viewModel.status.value) {
                    is ApiResponseStatus.Loading -> {
                        LoadingWheel()
                    }

                    is ApiResponseStatus.Error -> {
                        ErrorDialog(status.messageId) {
                            finish()
                        }
                    }

                    is ApiResponseStatus.Success -> {
                        DogListScreen(
                            dogList = status.data as List<Dog>,
                            onDogClicked = ::openDogDetailActivity,
                            onNavigationIconClick = ::onNavigationIconClick
                        )
                    }
                }

            }
        }

    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick() {
        finish()
    }
}
