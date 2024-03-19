package com.juanferdev.appperrona.doglist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.juanferdev.appperrona.DOG_KEY
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.dogdetail.DogDetailComposeActivity
import com.juanferdev.appperrona.dogdetail.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.models.Dog

class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPerronaTheme {
                val status = viewModel.status.value
                when (status) {
                    is ApiResponseStatus.Loading -> {
                        LoadingWheel()
                    }

                    is ApiResponseStatus.Error -> {
                        Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                    }

                    is ApiResponseStatus.Success -> {
                        DogListScreen(
                            dogList = status.data as List<Dog>,
                            onDogClicked = ::openDogDetailActivity
                        )
                    }
                }

            }
        }

    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }
}


@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Red
        )
    }
}
