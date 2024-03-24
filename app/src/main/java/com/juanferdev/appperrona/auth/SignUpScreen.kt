package com.juanferdev.appperrona.auth

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.composables.AuthField
import com.juanferdev.appperrona.composables.BackTopAppBar

@Composable
fun SignUpScreen(
    onBackClick: (() -> Unit)
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
        Content(modifier = Modifier.padding(it))
    }
}

@Composable
fun Content(modifier: Modifier) {
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
            },
            label = stringResource(id = R.string.email)
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            password.value,
            onTextChanged = { newValue ->
                password.value = newValue
            },
            label = stringResource(id = R.string.password),
            visualTransformation = PasswordVisualTransformation()
        )
        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            confirmPassword.value,
            onTextChanged = { newValue ->
                confirmPassword.value = newValue
            },
            label = stringResource(id = R.string.confirm_password),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { }
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }

}