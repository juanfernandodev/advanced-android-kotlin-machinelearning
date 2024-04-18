package com.juanferdev.appperrona.core.composables

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String) {
    TopAppBar(
        modifier = Modifier.background(Color.Red),
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}