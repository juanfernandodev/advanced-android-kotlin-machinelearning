package com.juanferdev.appperrona.doglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.composables.BackTopAppBar
import com.juanferdev.appperrona.composables.ErrorDialog
import com.juanferdev.appperrona.composables.LoadingWheel

private const val GRID_SPAN_COUNT = 3

@Composable
fun DogListScreen(
    onNavigationIconClick: () -> Unit,
    onDogClicked: (com.juanferdev.appperrona.core.models.Dog) -> Unit,
    viewModel: DogListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) { paddingValues ->

        when (val status = viewModel.status.value) {
            is com.juanferdev.appperrona.core.api.ApiResponseStatus.Loading -> {
                LoadingWheel()
            }

            is com.juanferdev.appperrona.core.api.ApiResponseStatus.Error -> {
                ErrorDialog(status.messageId) {
                    onNavigationIconClick()
                }
            }

            is com.juanferdev.appperrona.core.api.ApiResponseStatus.Success -> {
                val listDog = status.data
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = 20.dp, bottom = 40.dp),
                    columns = GridCells.Fixed(GRID_SPAN_COUNT)
                ) {
                    items(listDog) {
                        DogGridItem(dog = it, onDogClicked)
                    }
                }
            }
        }
    }
}

@Composable
fun DogListScreenTopBar(onClick: () -> Unit) {
    BackTopAppBar(
        title = stringResource(R.string.my_dog_collection),
        onClick = onClick
    )
}

@Composable
fun DogGridItem(
    dog: com.juanferdev.appperrona.core.models.Dog,
    onDogClicked: (com.juanferdev.appperrona.core.models.Dog) -> Unit
) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            AsyncImage(
                model = dog.imageUrl,
                contentDescription = dog.name,
                modifier = Modifier
                    .background(Color.White)
                    .semantics {
                        testTag = "dog-${dog.name}"
                    }
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .semantics {
                        testTag = "dog-${dog.index}"
                    },
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

