package com.juanferdev.appperrona.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.juanferdev.appperrona.DOG_KEY
import com.juanferdev.appperrona.IS_RECOGNITION_KEY
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.dogdetail.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.models.Dog
import com.juanferdev.appperrona.utils.parcelable

class DogDetailComposeActivity : ComponentActivity() {

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent?.parcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false
        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_LONG).show()
            finish()
        } else {
            setContent {
                when (val status = viewModel.status.value) {
                    is ApiResponseStatus.Error -> {
                        ErrorDialog(status.messageId, onDialogDismiss = ::resetApiResponseStatus)
                    }

                    is ApiResponseStatus.Loading -> {
                        LoadingWheel()
                    }

                    is ApiResponseStatus.Success -> {
                        Toast.makeText(this, stringResource(R.string.dog_saved), Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }

                    null -> {
                        AppPerronaTheme {
                            DogDetailScreen(
                                dog = dog,
                                onClickButtonDetailActivity = {
                                    onClickButtonDetailActivity(
                                        isRecognition,
                                        dog.id
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

    }

    private fun onClickButtonDetailActivity(isRecognition: Boolean, dogId: Long) {
        if (isRecognition) {
            viewModel.addDogToUser(dogId)
        } else {
            finish()
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
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

@Composable
fun ErrorDialog(
    errorMessageId: Int,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.oops_something_happend))
        },
        text = {
            Text(text = stringResource(id = errorMessageId))
        },
        onDismissRequest = {

        },
        confirmButton = {
            Button(
                onClick = { onDialogDismiss() }
            ) {
                Text(stringResource(id = R.string.try_again))
            }
        }
    )
}



