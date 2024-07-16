package com.example.pocketstorage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.pocketstorage.R

@Composable
fun EditorNameComponent(
    productName : MutableState<String>,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.padding(bottom = 8.dp),
                value = productName.value,
                onValueChange = { productName.value = it },
                label = { Text("Product Name") },
                colors = if (productName.value.isNullOrEmpty()) {
                    TextFieldDefaults.colors(
                        focusedLabelColor = Color.Red,
                        unfocusedLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Red
                    )
                } else{
                    TextFieldDefaults.colors(
                        focusedLabelColor = colorResource(id = R.color.AdamantineBlue),
                        unfocusedLabelColor = colorResource(id = R.color.SpanishGrey),
                        focusedIndicatorColor = colorResource(id = R.color.AdamantineBlue))
                }
            )
            Button(
                onClick = { onSave(productName.value) },
                colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.RetroBlue))
            ) {
                Text("Save")
            }
        }
    }
}