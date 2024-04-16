package com.juanferdev.appperrona.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.res.stringResource
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.composables.ErrorDialog
import com.juanferdev.appperrona.composables.LoadingWheel
import com.juanferdev.appperrona.constants.DOG_KEY
import com.juanferdev.appperrona.constants.IS_RECOGNITION_KEY
import com.juanferdev.appperrona.constants.PROBABLES_DOG_ID_KEY
import com.juanferdev.appperrona.dogdetail.ui.theme.AppPerronaTheme
import com.juanferdev.appperrona.models.Dog
import com.juanferdev.appperrona.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailActivity : ComponentActivity() {

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent?.parcelable<Dog>(DOG_KEY)
        val probablesDogIds = intent?.extras?.getStringArrayList(PROBABLES_DOG_ID_KEY) ?: listOf()
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
                                isRecognition = isRecognition,
                                dog = dog,
                                probableDogsIds = probablesDogIds,
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
