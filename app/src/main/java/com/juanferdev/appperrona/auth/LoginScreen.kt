package com.juanferdev.appperrona.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.composables.AuthField
import com.juanferdev.appperrona.composables.TopAppBar

@Composable
fun LoginScreen(
    onClickRegister: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit,
    viewModel: AuthViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(
                    id = R.string.app_name
                )
            )
        }
    ) {
        Content(
            modifier = Modifier.padding(paddingValues = it),
            onLoginButtonClick = onLoginButtonClick,
            onClickRegister = onClickRegister,
            viewModel = viewModel
        )
    }

}


@Composable
fun Content(
    modifier: Modifier,
    onClickRegister: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit,
    viewModel: AuthViewModel
) {
    val email = remember { mutableStateOf(String()) }
    val password = remember { mutableStateOf(String()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
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
            errorMessageId =
            if (viewModel.authFieldStatus.value is AuthFieldStatus.Password) {
                (viewModel.authFieldStatus.value as AuthFieldStatus.Password).messageId
            } else {
                null
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onLoginButtonClick(email.value, password.value) }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.do_not_have_an_account)
        )
        Text(
            modifier = Modifier
                .clickable(true, onClick = { onClickRegister() })
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }

}
