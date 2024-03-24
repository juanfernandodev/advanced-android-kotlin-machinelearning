package com.juanferdev.appperrona.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus

@Composable
fun LoginScreen(
    status: ApiResponseStatus<Any>? = null
) {
    Scaffold(topBar = { LoginScreenToolbar() }) {
        Content(modifier = Modifier.padding(paddingValues = it))
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenToolbar() {
    TopAppBar(
        modifier = Modifier.background(Color.Red),
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

@Composable
fun Content(modifier: Modifier) {
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
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { }
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
                .clickable(true, onClick = {})
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }

}

@Composable
fun AuthField(
    modifier: Modifier,
    email: String,
    onTextChanged: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier,
        value = email,
        onValueChange = { newValue -> onTextChanged(newValue) },
        label = { Text(label) },
        visualTransformation = visualTransformation
    )
}