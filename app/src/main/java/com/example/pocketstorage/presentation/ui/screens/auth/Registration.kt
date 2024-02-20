package com.example.pocketstorage.presentation.ui.screens.auth

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pocketstorage.R
import com.example.pocketstorage.presentation.ui.screens.auth.viewmodel.RegistrationViewModel

/*@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {

    val authViewModel = viewModel<RegistrationViewModel>()

    RegistrationScreen(
        onSignUpClick = {authViewModel.signUpWithEmailAndPassword(authViewModel.email.value,authViewModel.password.value)},
        authViewModel

    )
}*/

@Composable
fun RegistrationScreen(
    onSignUpClickDone: () -> Unit,
    authViewModel: RegistrationViewModel
) {
    val screenState by authViewModel.screenState.collectAsState()
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current
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
            },
            value = uiState.email,
            onValueChange = authViewModel::onEmailChange
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
            },
            value = uiState.password,
            onValueChange = authViewModel::onPasswordChange
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
            },
            value = uiState.repeatPassword,
            onValueChange = authViewModel::onRepeatPasswordChange
        )

        ButtonLogInAuthorizationApp(
            onClick = {
                if (uiState.password==uiState.repeatPassword){
                    Log.d("pass", "${uiState.password } ${uiState.repeatPassword}")
                    authViewModel.signUpWithEmailAndPassword(
                        authViewModel.uiState.value.email,
                        authViewModel.uiState.value.password
                    )
                } else{
                    Toast.makeText(
                        context,
                        "Пароли не совпадают",
                        Toast.LENGTH_LONG
                    ).show()
                }

            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.MildGreen)),
            text = stringResource(id = R.string.sign_up),
            iconResource = painterResource(id = R.drawable.account_plus)
        )

        if (screenState.success) {
            Toast.makeText(
                context,
                "Пользователь ${uiState.email} зарегистрирован",
                Toast.LENGTH_LONG
            ).show()
            onSignUpClickDone()
        }


    }
}