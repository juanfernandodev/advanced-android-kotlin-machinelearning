package com.juanferdev.appperrona.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    modifier: Modifier,
    email: String,
    onTextChanged: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessageId: Int? = null
) {
    Column(
        modifier = modifier
    ) {
        if (errorMessageId != null) {
            Text(
                text = stringResource(id = errorMessageId)
            )
        }
        OutlinedTextField(
            modifier = modifier,
            value = email,
            onValueChange = { newValue -> onTextChanged(newValue) },
            label = { Text(label) },
            visualTransformation = visualTransformation,
            isError = errorMessageId != null
        )
    }

}