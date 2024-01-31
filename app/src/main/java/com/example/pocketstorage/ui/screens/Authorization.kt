package com.example.pocketstorage.ui.screens

import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocketstorage.R

/*@Composable
fun Authorization() {
    AuthorizationScreen()
}*/

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp
@Composable
fun TextMainApp(nameApp: String) {
    Text(
        modifier = Modifier.padding(top = 80.dp, bottom = 50.dp),
        text = nameApp,
        color = Color.Black,
        fontSize = fontDimensionResource(id = R.dimen.text_size_26sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAuthorizationApp(
    textHint: String,
    color: Color,
    modifier: Modifier,
    keyOption: KeyboardOptions,
    visualTransformation: PasswordVisualTransformation,
    icon: @Composable () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text(text = textHint, color = color) },
        leadingIcon = icon,
        keyboardOptions = keyOption,
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAuthorizationApp(
    textHint: String,
    color: Color,
    modifier: Modifier,
    keyOption: KeyboardOptions,
    icon: @Composable () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text(text = textHint, color = color) },
        leadingIcon = icon,
        keyboardOptions = keyOption
    )
}

@Composable
fun ButtonLogInAuthorizationApp(onClick: () -> Unit) {

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.RetroBlue)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .size(height = 48.dp, width = 218.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_login_24),
            contentDescription = "logIn",
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = "Log In / Sign up", color = Color.White)

    }
}
@Composable
fun ButtonSignUpAuthorizationApp(onClick: () -> Unit) {

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.RetroBlue)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .size(height = 48.dp, width = 218.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_login_24),
            contentDescription = "logIn",
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = "Sign up", color = Color.White)

    }
}

@Composable
fun ButtonContinueApp(onClick: () -> Unit) {

    OutlinedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(height = 48.dp, width = 312.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.RetroBlue))
    ) {
        Text(text = "Continue without registration", color = colorResource(R.color.RetroBlue))

    }
}

@Composable
fun AuthorizationScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextMainApp(nameApp = "Pocket Storage")

        TextFieldAuthorizationApp(
            textHint = "Email",
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 10.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Email)
        ) { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon") }

        TextFieldAuthorizationApp(
            textHint = "Password",
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 36.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        ) { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon") }

        ButtonLogInAuthorizationApp {
            //обработка нажатия с регистрацией
        }
        ButtonSignUpAuthorizationApp {
            onSignUpClick()
        }

        ButtonContinueApp {
            // обработка нажатия без регистрации
            onClick()
        }

        Spacer(modifier = Modifier.weight(1f)) // Добавляем Spacer для занятия доступного пространства

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 26.dp, end = 24.dp),
            text = "v 1.0.0",
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorization() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextMainApp(nameApp = "Pocket Storage")

        TextFieldAuthorizationApp(
            textHint = "Email",
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 10.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Email)
        ) { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon") }

        TextFieldAuthorizationApp(
            textHint = "Password",
            color = colorResource(R.color.SpanishGrey),
            modifier = Modifier.padding(bottom = 36.dp),
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        ) { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon") }

        ButtonLogInAuthorizationApp {
            //обработка нажатия с регистрацией
        }

        ButtonContinueApp {
            // обработка нажатия без регистрации
        }

        Spacer(modifier = Modifier.weight(1f)) // Добавляем Spacer для занятия доступного пространства

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 26.dp, end = 24.dp),
            text = "v 1.0.0",
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}
