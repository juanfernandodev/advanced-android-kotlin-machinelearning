package com.juanferdev.appperrona.auth.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanferdev.appperrona.core.R
import com.juanferdev.appperrona.core.composables.AuthField
import com.juanferdev.appperrona.core.composables.BackTopAppBar
import com.juanferdev.appperrona.core.constants.SemanticConstants
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_ERROR_TEXT_CONFIRMATION_PASSWORD_FIELD
import com.juanferdev.appperrona.core.constants.SemanticConstants.SEMANTIC_SIGN_UP_BUTTON

@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel
) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = stringResource(
                    id = R.string.sign_up
                ),
                onClick = onBackClick
            )
        }
    ) {
        Content(
            modifier = Modifier.padding(it), onSignUpButtonClick,
            viewModel = viewModel
        )
    }
}

@Composable
fun Content(
    modifier: Modifier,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel
) {
    val email = remember { mutableStateOf(String()) }
    val password = remember { mutableStateOf(String()) }
    val confirmPassword = remember { mutableStateOf(String()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email.value,
            onTextChanged = { newValue ->
                email.value = newValue
                viewModel.resetAuthFieldStatus()
            },
            label = stringResource(id = R.string.email),
            errorSemantic = SemanticConstants.SEMANTIC_ERROR_TEXT_EMAIL_FIELD,
            fieldSemantic = SemanticConstants.SEMANTIC_EMAIL_FIELD,
            errorMessageId =
            if (viewModel.authFieldStatus.value is AuthFieldStatus.Email) {
                (viewModel.authFieldStatus.value as AuthFieldStatus.Email).messageId
            } else {
                null
            }
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            password.value,
            onTextChanged = { newValue ->
                password.value = newValue
                viewModel.resetAuthFieldStatus()
            },
            label = stringResource(id = R.string.password),
            visualTransformation = PasswordVisualTransformation(),
            errorSemantic = SemanticConstants.SEMANTIC_ERROR_TEXT_PASSWORD_FIELD,
            fieldSemantic = SemanticConstants.SEMANTIC_PASSWORD_FIELD,
            errorMessageId =
            if (viewModel.authFieldStatus.value is AuthFieldStatus.Password) {
                (viewModel.authFieldStatus.value as AuthFieldStatus.Password).messageId
            } else {
                null
            }
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            confirmPassword.value,
            onTextChanged = { newValue ->
                confirmPassword.value = newValue
                viewModel.resetAuthFieldStatus()
            },
            label = stringResource(id = R.string.confirm_password),
            visualTransformation = PasswordVisualTransformation(),
            errorSemantic = SEMANTIC_ERROR_TEXT_CONFIRMATION_PASSWORD_FIELD,
            fieldSemantic = SemanticConstants.SEMANTIC_CONFIRMATION_PASSWORD_FIELD,
            errorMessageId =
            if (viewModel.authFieldStatus.value is AuthFieldStatus.ConfirmPassword) {
                (viewModel.authFieldStatus.value as AuthFieldStatus.ConfirmPassword).messageId
            } else {
                null
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics {
                    testTag = SEMANTIC_SIGN_UP_BUTTON
                },
            onClick = { onSignUpButtonClick(email.value, password.value, confirmPassword.value) }
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }

}