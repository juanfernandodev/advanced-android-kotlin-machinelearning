package com.juanferdev.appperrona.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hackaprende.dogedex.core.dogdetail.MostProbableDogsDialog
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.composables.ErrorDialog
import com.juanferdev.appperrona.composables.LoadingWheel
import com.juanferdev.appperrona.constants.SemanticConstants.SEMANTIC_DETAIL_DOG_BUTTON
import com.juanferdev.appperrona.models.Dog

@Composable
fun DogDetailScreen(
    dogDetailViewModel: DogDetailViewModel = hiltViewModel(),
    finishActivity: (Int?) -> Unit,
) {
    val dog = dogDetailViewModel.dog.value
    if (dog != null) {

        val isRecognition = dogDetailViewModel.isRecognition.value
        when (val status = dogDetailViewModel.status.value) {
            is ApiResponseStatus.Error -> {
                ErrorDialog(
                    status.messageId,
                    onDialogDismiss = { dogDetailViewModel.resetApiResponseStatus() })
            }

            is ApiResponseStatus.Loading -> {
                LoadingWheel()
            }

            is ApiResponseStatus.Success -> {
                finishActivity(R.string.dog_saved)
            }

            null -> {
                //Its only used to reset the status
            }
        }
        val probablesDogsList =
            dogDetailViewModel.probableDogList.collectAsStateWithLifecycle().value
        CardDogDetail(
            dog = dog,
            isRecognition = isRecognition,
            addDogToUser = { dogId -> dogDetailViewModel.addDogToUser(dogId) },
            finishActivity = { messageId -> finishActivity(messageId) },
            probablesDogsList = probablesDogsList,
            getProbablesDogs = { dogDetailViewModel.getProbableDogs() }
        )
    } else {
        finishActivity(R.string.error_adding_dog)
    }
}

@Composable
private fun CardDogDetail(
    dog: Dog,
    isRecognition: Boolean,
    addDogToUser: (Long) -> Unit,
    finishActivity: (Int?) -> Unit,
    probablesDogsList: List<Dog>,
    getProbablesDogs: () -> Unit
) {
    val probableDogsDialogEnabled = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(
                colorResource(
                    id = R.color.secondary_background
                )
            )
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        DogInformation(
            dog = dog,
            isRecognition = isRecognition,
        ) {
            getProbablesDogs()
            probableDogsDialogEnabled.value = true
        }
        AsyncImage(
            model = dog.imageUrl,
            contentDescription = dog.name,
            modifier = Modifier
                .width(270.dp)
                .padding(top = 80.dp)
        )

        if (probableDogsDialogEnabled.value) {
            MostProbableDogsDialog(
                mostProbableDogs = probablesDogsList,
                onShowMostProbableDogsDialogDismiss = {
                    probableDogsDialogEnabled.value = false
                },
                onItemClicked = {}
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .semantics {
                    testTag = SEMANTIC_DETAIL_DOG_BUTTON
                },
            onClick = {
                if (isRecognition) {
                    addDogToUser(dog.id)
                } else {
                    finishActivity(null)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun DogInformation(
    dog: Dog,
    isRecognition: Boolean,
    onProbableDogsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 180.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(id = android.R.color.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text =
                    stringResource(
                        R.string.dog_index_format,
                        dog.index
                    ),
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 32.dp,
                            bottom = 8.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                    text = dog.name,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )
                LifeIcon()
                Text(
                    text = stringResource(
                        id = R.string.dog_life_expectancy_format,
                        dog.lifeExpectancy
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black)
                )
                Text(
                    text = dog.temperament,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                if (isRecognition) {
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onProbableDogsButtonClick() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.not_your_dog),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ),
                    color = colorResource(id = R.color.divider),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DogDetailColumn(
                        modifier = Modifier.weight(1f),
                        gender = stringResource(
                            id = R.string.female
                        ),
                        weight = dog.weightFemale,
                        height = dog.heightFemale
                    )
                    VerticalDividerCustom()
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dog.type,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_black),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.group),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.dark_gray),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    VerticalDividerCustom()
                    DogDetailColumn(
                        modifier = Modifier.weight(1f),
                        gender = stringResource(
                            id = R.string.male
                        ),
                        weight = dog.weightMale,
                        height = dog.heightMale
                    )
                }
            }
        }
    }

}

@Composable
fun VerticalDividerCustom() {
    VerticalDivider(
        modifier = Modifier
            .height(42.dp)
            .width(1.dp),
        color = colorResource(id = R.color.divider)
    )
}

@Composable
private fun DogDetailColumn(
    modifier: Modifier,
    gender: String,
    weight: String,
    height: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = gender,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black)
        )
        Text(
            text = weight,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_black),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )
        Text(
            text = height,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_black),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )
    }
}

@Composable
private fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = colorResource(id = R.color.color_primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hearth_white),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp)
            )
        }

        Surface(
            shape = RoundedCornerShape(
                bottomEnd = 2.dp,
                topEnd = 2.dp
            ),
            modifier = Modifier
                .width(200.dp)
                .height(6.dp),
            color = colorResource(id = R.color.color_primary)
        ) {

        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val dog = Dog(
        1L,
        78,
        "Pug",
        "Herding",
        "70",
        "75",
        "",
        lifeExpectancy = "10 - 12",
        "Friendly, playful",
        "5",
        "6"
    )

    CardDogDetail(
        addDogToUser = {},
        dog = dog,
        finishActivity = {},
        isRecognition = true,
        getProbablesDogs = {},
        probablesDogsList = mutableListOf()
    )

}
