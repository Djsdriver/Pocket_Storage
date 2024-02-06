package com.example.pocketstorage.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pocketstorage.R

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        onSignUpClick = {}
    )
}

@Composable
fun RegistrationScreen(
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextMainApp(nameApp = stringResource(id = R.string.sign_up))

        TextFieldAuthorizationApp(
            textHint = stringResource(id = R.string.email_or_telephone),
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 10.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Text),
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.email_or_telephone)
                )
            }
        )

        TextFieldAuthorizationApp(
            textHint = stringResource(id = R.string.password),
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 10.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
            icon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.password)
                )
            }
        )

        TextFieldAuthorizationApp(
            textHint = stringResource(id = R.string.repeat_password),
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 48.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
            icon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.repeat_password)
                )
            }
        )

        ButtonLogInAuthorizationApp(
            onClick = { onSignUpClick() },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.MildGreen)),
            text = stringResource(id = R.string.sign_up),
            iconResource = painterResource(id = R.drawable.account_plus)
        )
    }
}