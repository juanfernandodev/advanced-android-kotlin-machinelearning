package com.juanferdev.appperrona.core.composables

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopAppBar(title: String, color: Color = Color.White, onClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.background(color),
        title = { Text(title) },
        navigationIcon = { BackNavigationIcon(onClick) },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

@Composable
fun BackNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter = rememberVectorPainter(image = Icons.AutoMirrored.Sharp.ArrowBack),
            contentDescription = null
        )
    }
}