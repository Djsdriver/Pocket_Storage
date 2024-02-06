package com.example.pocketstorage.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pocketstorage.R

@Composable
fun Auth(onClick: () -> Unit) {
    AuthScaffold(onClick)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
//@Preview(showBackground = true)
fun AuthScaffold(onClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create Building")
                },
                navigationIcon = {
                    IconButton(onClick = { onClick() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
        content = { paddingValues -> // Добавлены paddingValues
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 24.dp, top = paddingValues.calculateTopPadding(), end = 24.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(24.dp)) // Добавлен Spacer с высотой 24dp
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    InputMailAndPass(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = {
                            Text(
                                text = "Mail",
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        )
                    )
                    InputMailAndPass(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = {
                            Text(
                                text = "pass",
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        )
                    )


                    Create {
                        onClick()
                    }
                }
            }

        }
    )
}

@Composable
fun Create(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.RetroBlue)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 200.dp)
    ) {
        Text(text = "Create", color = Color.White)
    }
}



@Composable
fun InputMailAndPass(
    modifier: Modifier,
    label: @Composable () -> Unit,
    colors: TextFieldColors
) {
    var state by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = state,
        onValueChange = { state = it },
        label = label,
        colors = colors
    )
}