package com.juanferdev.appperrona.core.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    modifier: Modifier,
    email: String,
    onTextChanged: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessageId: Int? = null,
    errorSemantic: String,
    fieldSemantic: String
) {
    Column(
        modifier = modifier
    ) {
        if (errorMessageId != null) {
            Text(
                text = stringResource(id = errorMessageId),
                modifier = Modifier.semantics {
                    testTag = errorSemantic
                }
            )
        }
        OutlinedTextField(
            modifier = modifier
                .semantics {
                    testTag = fieldSemantic
                },
            value = email,
            onValueChange = { newValue -> onTextChanged(newValue) },
            label = { Text(label) },
            visualTransformation = visualTransformation,
            isError = errorMessageId != null
        )
    }

}