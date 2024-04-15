package com.juanferdev.appperrona.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.composables.constants.SEMANTIC_ERROR_DIALOG

@Composable
fun ErrorDialog(
    errorMessageId: Int,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.semantics {
            testTag = SEMANTIC_ERROR_DIALOG
        },
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