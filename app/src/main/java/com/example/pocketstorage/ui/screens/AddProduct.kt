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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pocketstorage.R

@Composable
fun CreateProduct() {
    addProduct()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun addProduct() {

    /*TopAppBar(
        title = { Text(text = "Create Product") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back"
            )
        })*/
    ScaffoldWithTopBarProductPage()

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldWithTopBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create Product")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
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
                TextFieldCreateProduct(
                    textHint = "name",
                    colorHint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                AddWithoutCategory()
                TextFieldCreateProduct(
                    textHint = "description (optional)",
                    colorHint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(107.dp)
                        .fillMaxWidth()
                )

                ButtonSaveProductPage {
                    //Click
                }

            }


        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCreateProduct(
    textHint: String,
    colorHint: Color,
    modifier: Modifier,
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text(text = textHint, color = colorHint) },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.purple_700),
            unfocusedBorderColor = colorResource(id = R.color.purple_500)
        )
    )

}

@Composable
fun ButtonSaveProduct(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.purple_500)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 200.dp)
    ) {
        Text(text = "Save", color = Color.White)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWithoutCategory() {
    val moviesList = listOf(
        "Iron Man",
        "Thor: Ragnarok",
        "Captain America: Civil War",
        "Doctor Strange",
        "The Incredible Hulk",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
        "Ant-Man and the Wasp",
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf(moviesList[0]) }

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // textfield
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(), // menuAnchor modifier must be passed to the text field for correctness.
            readOnly = true,
            value = selectedMovie,
            onValueChange = {},
            label = { Text(text = "Without category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.purple_700),
                unfocusedBorderColor = colorResource(id = R.color.purple_500)
            )
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            // menu items
            moviesList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedMovie = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

