package com.example.pocketstorage.presentation.ui.screens.building


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.presentation.ui.screens.building.viewmodel.BuildingViewModel


@Composable
fun Building(viewModel: BuildingViewModel = hiltViewModel(), onClick: () -> Unit) {
    BuildingScreen(viewModel, onClick)
}

//@Preview(showBackground = true)
@Composable
fun PreviewBuildingScreen(viewModel: BuildingViewModel) {
    BuildingScreen(viewModel, onClick = {})
}

@Composable
fun BuildingScreen(viewModel: BuildingViewModel, onClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(true) {
        viewModel.refreshLocations()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 56.dp, start = 24.dp, bottom = 24.dp, end = 24.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f)) {
                TextFieldSearchBuildingName(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = "building",
                            color = colorResource(id = R.color.SpanishGrey)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "SearchById"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.RetroBlue),
                        unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                    ),
                    value = state.searchText,
                    onValueChange = viewModel::onSearchTextChange
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 24.dp, bottom = 16.dp),
        ) {
            ButtonBuildingScreen(
                modifier = Modifier.wrapContentWidth(),
                rowContent = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add building",
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Text(text = "Add building", fontSize = 16.sp)
                },
                onClick = {
                    onClick()
                }
            )
        }
        RenderScreen(viewModel,uiState)
    }
}

@Composable
private fun RenderScreen(viewModel: BuildingViewModel,uiState: BuildingUiState) {
    when (val currentState = uiState) {
        is BuildingUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is BuildingUiState.Success -> {
            LaunchedEffect(Unit){
                viewModel.getLocationIdFromDataStore()
            }

            if (currentState.isEmpty()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "No locations", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .background(Color.White)
                ) {
                    items(currentState.locations) { location ->
                        ListRowBuilding(model = location){buildingId->
                            viewModel.saveBuildingId(buildingId)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TextFieldSearchBuildingName(
    modifier: Modifier,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    colors: TextFieldColors,
    value: String,
    onValueChange: (String) -> Unit

) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange, // тоже можно использовать такую конструкцию onValueChange = { newText-> viewModel.onSearchTextChange(newText) }
        label = label,
        leadingIcon = leadingIcon,
        colors = colors
    )
}


@Composable
fun ButtonBuildingScreen(
    modifier: Modifier,
    rowContent: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.RetroBlue)),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            rowContent()
        }
    }
}

@Composable
fun ListRowBuilding(model: Location, onItemsSelected: (String) -> Unit) {
    val viewModel = hiltViewModel<BuildingViewModel>()

    val buildingState by viewModel.state.collectAsState()
    val animatedColorState = animateColorAsState(
        targetValue = if (model.id == buildingState.selectedIdBuilding) colorResource(id = R.color.SpanishGrey) else colorResource(id = R.color.AdamantineBlue),
        label = ""
    )
    val animatedHeightState = animateDpAsState(
        targetValue = if (model.id == buildingState.selectedIdBuilding) 90.dp else 80.dp,
        label = ""
    )
    val animatedWidthState = animateDpAsState(
        targetValue = if (model.id == buildingState.selectedIdBuilding) 300.dp else 310.dp,
        label = ""
    )


    Column(
        modifier = Modifier
            .padding(2.dp)
            .size(width = animatedWidthState.value, height = animatedHeightState.value)
            .clip(RoundedCornerShape(8.dp))
            .background(animatedColorState.value)
            .clickable {
                onItemsSelected(model.id)
            }
            .offset(x = 0.dp, y = 0.dp)
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
            }
            .padding(horizontal = 8.dp), // Добавлено горизонтальное отступание для центрирования
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = model.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(),
            textAlign = TextAlign.Center,
            text = model.index,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            text = model.address,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
    }
}

