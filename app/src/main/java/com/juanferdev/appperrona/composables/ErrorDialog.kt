package com.juanferdev.appperrona.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.juanferdev.appperrona.R

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