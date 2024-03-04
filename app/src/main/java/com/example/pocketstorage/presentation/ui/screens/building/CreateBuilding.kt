package com.example.pocketstorage.presentation.ui.screens.building

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketstorage.R
import com.example.pocketstorage.presentation.ui.screens.building.viewmodel.CreateBuildingViewModel


/*@Preview(showBackground = true)
@Composable
fun PreviewB(){
    CreateBuilding {

    }
}*/
@Composable
fun CreateBuilding(onEvent: (CreateBuildingEvent) -> Unit,onClick: () -> Unit) {
    ScaffoldWithTopBarCreatingBuilding(onEvent,onClick)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
//@Preview(showBackground = true)
fun ScaffoldWithTopBarCreatingBuilding(onEvent: (CreateBuildingEvent) -> Unit,onClick: () -> Unit) {
    val viewModel = hiltViewModel<CreateBuildingViewModel>()
    val state by viewModel.state.collectAsState()
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

                    TextFieldCreateBuilding(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = {
                            Text(
                                text = "name",
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        ),
                        value = state.name,
                        onValueChange = {onEvent(CreateBuildingEvent.SetNameBuilding(it))}
                    )
                    TextFieldCreateBuilding(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = {
                            Text(
                                text = "address",
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        ),
                        value = state.address,
                        onValueChange = {onEvent(CreateBuildingEvent.SetAddress(it))}
                    )
                    TextFieldCreateBuilding(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {
                            Text(
                                text = "short code (ex: MSK-1)",
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        ),
                        value = state.index,
                        onValueChange = {onEvent(CreateBuildingEvent.SetIndex(it))}
                    )

                    ButtonSaveBuilding {
                        onClick()
                        onEvent(CreateBuildingEvent.CreateBuilding)
                    }
                }
            }

        }
    )
}

@Composable
fun ButtonSaveBuilding(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.RetroBlue)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 200.dp)
    ) {
        Text(text = "Save", color = Color.White)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCreateBuilding(
    modifier: Modifier,
    label: @Composable () -> Unit,
    colors: TextFieldColors,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange =  { onValueChange(it) },
        label = label,
        colors = colors
    )
}