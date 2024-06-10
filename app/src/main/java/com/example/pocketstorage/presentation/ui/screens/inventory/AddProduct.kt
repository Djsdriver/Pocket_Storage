package com.example.pocketstorage.presentation.ui.screens.inventory

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.presentation.ui.screens.inventory.event.CreateProductEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.AddProductViewModel
import com.example.pocketstorage.utils.SnackbarManager
import com.example.pocketstorage.utils.SnackbarMessage
import com.example.pocketstorage.utils.SnackbarMessage.Companion.toMessage
import java.io.FileNotFoundException
import kotlin.math.min

@Composable
fun CreateProduct(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: AddProductViewModel = hiltViewModel(),
    onEvent: (CreateProductEvent) -> Unit
) {
    AddProductScreen(
        onBackArrowClick = onBackArrowClick,
        onAddPictureClick = onAddPictureClick,
        onSaveClick = onSaveClick,
        viewModel,
        onEvent = onEvent
    )
}

/*@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview(
    viewModel: AddProductViewModel = hiltViewModel()
) {
    AddProductScreen(
        onBackArrowClick = {},
        onAddPictureClick = {},
        onGenerateQRClick = {},
        onSaveClick = {},
        viewModel
    )
}*/

@Composable
fun AddProductScreen(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: AddProductViewModel,
    onEvent: (CreateProductEvent) -> Unit
) {
    ScaffoldBase(
        onBackArrowClick = onBackArrowClick,
        onAddPictureClick = onAddPictureClick,
        onSaveClick = onSaveClick,
        onEvent = onEvent
    )
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ScaffoldBase(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onSaveClick: () -> Unit,
    onEvent: (CreateProductEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onBackArrowClick = onBackArrowClick)
        },
        content = { paddingValues ->
            BaseContent(
                paddingValues = paddingValues,
                onAddPictureClick = onAddPictureClick,
                onSaveClick = onSaveClick,
                onEvent = onEvent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackArrowClick: () -> Unit) {
    val snackbarMessage by SnackbarManager.snackbarMessages.collectAsState()
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = "Create Product")
        },
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        actions = {
            SnackBarToast(snackbarMessage = snackbarMessage, context = context)
        }
    )
}

@Composable
fun BaseContent(
    paddingValues: PaddingValues,
    onAddPictureClick: () -> Unit,
    onSaveClick: () -> Unit,
    topPadding: Dp = 8.dp,
    viewModel: AddProductViewModel = hiltViewModel(),
    onEvent: (CreateProductEvent) -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    var buildingIdString by remember {
        mutableStateOf("")
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                start = 24.dp,
                top = paddingValues.calculateTopPadding(),
                end = 24.dp
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        AddPictureCard(onClick = onAddPictureClick, onEvent = viewModel::event, viewModel)
        BaseTextField(
            textHint = "name",
            colorHint = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth(),
            value = state.name,
            onValueChange = { onEvent(CreateProductEvent.SetNameProduct(it)) }
        )
        BaseTextField(
            textHint = "description (optional)",
            colorHint = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth()
                .height(112.dp),
            value = state.description,
            onValueChange = { onEvent(CreateProductEvent.SetDescription(it)) }
        )
        LaunchedEffect(Unit) {
            onEvent(CreateProductEvent.ShowListBuilding)
        }

        LaunchedEffect(state.locations) {
            onEvent(CreateProductEvent.ShowListCategory(state.locationId))
        }

        BaseDropdownMenu(
            listOfElements = state.locations,
            modifier = Modifier.padding(top = topPadding)
        ) { selectedLocationName ->
            onEvent(CreateProductEvent.SetLocationId(selectedLocationName))
            onEvent(CreateProductEvent.ShowListCategory(selectedLocationName))
            buildingIdString = selectedLocationName

        }
        BaseDropdownMenuCategory(
            listOfElements = state.categories, //заменить на категории
            modifier = Modifier.padding(top = topPadding)
        ) { selectedIdCategory ->
            onEvent(CreateProductEvent.SetCategoryId(selectedIdCategory))
        }

        BaseButton(
            onClick = {
                onEvent(CreateProductEvent.CreateBuilding)
                onSaveClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.RetroBlue)
            ),
            text = "Save",
            enabled = true
        )
    }
}


@SuppressLint("ResourceAsColor")
@Composable
fun AddPictureCard(
    onClick: () -> Unit,
    onEvent: (CreateProductEvent) -> Unit,
    viewModel: AddProductViewModel
) {

    val state by viewModel.state.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = with(LocalDensity.current) { screenWidth - 16.dp }


    val stroke = Stroke(
        width = 8f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f)
    )

    val color = remember {
        mutableStateOf(Color(0xff2d7cf3))
    }

    var pathToLoadingPicture by remember {
        mutableStateOf<String?>(null)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val painter = rememberAsyncImagePainter(model = bitmap.value)
    val painterState = painter.state

    val transition by animateFloatAsState(
        targetValue = if (painterState is AsyncImagePainter.State.Loading) 1f else 0f, label = "",
    )


    val launcherGallery =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            Log.d("uri", "$uri")
            imageUri = uri
            uri?.let { CreateProductEvent.SetPathToImage(it) }?.let { onEvent(it) }
        }

    val launcherCamera =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { photoBitmap ->
            bitmap.value = photoBitmap
            imageUri = null
            viewModel.savePathImageBitmap(bitmap.value!!)


            Log.d("path","$photoBitmap")



            pathToLoadingPicture = photoBitmap.toString()
            if (photoBitmap != null) {
                color.value = Color.Transparent
            } else {
                color.value = color.value
            }

           Log.d("launchCamera","$photoBitmap")
            Log.d("launchCamera","${bitmap.value}")
            Log.d("launchCamera","${pathToLoadingPicture}")

        }

    LaunchedEffect(state.pathToImage) {
        if (state.pathToImage != null) {
            val uri = Uri.parse(state.pathToImage)
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                pathToLoadingPicture = state.pathToImage
                color.value = Color.Transparent
            } catch (e: FileNotFoundException) {
                bitmap.value = null
                color.value = Color(0xff2d7cf3)
            }
        } else {
            bitmap.value = null
            color.value = Color(0xff2d7cf3)
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(UInt) {
                detectTapGestures(
                    onTap = {
                        launcherGallery.launch("image/*")
                    },
                    onLongPress = {
                        launcherCamera.launch()
                    }
                )
            }
            .aspectRatio(1f)
            .width(cardWidth)
            .drawBehind {
                drawRoundRect(
                    color = color.value,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            },
        border = BorderStroke(1.dp, Color.Transparent)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            if (bitmap.value != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(min(1f, transition / .2f))
                        .scale(.8f + (.2f * transition))
                        .graphicsLayer { rotationX = (1f - transition) * 5f },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(bitmap.value)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                // This will show the placeholder image if bitmap is null
                Icon(
                    painter = painterResource(id = R.drawable.add_photo_alternate),
                    contentDescription = "Add Photo",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color(R.color.SpanishGrey)
                )
            }
            pathToLoadingPicture?.let {
               // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun BaseTextField(
    textHint: String,
    colorHint: Color,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = textHint, color = colorHint) },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.AdamantineBlue),
            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
        )
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BaseDropdownMenu(
    listOfElements: List<Location>,
    modifier: Modifier,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedElement by remember { mutableStateOf(listOfElements.getOrNull(0)) }
    Log.d("element", "${selectedElement}")

    var value = "${selectedElement?.address} "

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // textfield
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedElement?.address ?: "without building",
            onValueChange = { value = it },
            label = { Text(text = "without building") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.AdamantineBlue),
                unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
            )
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // menu items
            listOfElements.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text("${selectionOption.name}\n${selectionOption.address}") },
                    onClick = {
                        selectedElement = selectionOption
                        expanded = false
                        onItemSelected(selectionOption.id)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BaseDropdownMenuCategory(
    listOfElements: List<Category>,
    modifier: Modifier,
    onItemSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedElement by remember { mutableStateOf(listOfElements.getOrNull(0)) }
    Log.d("element", "${selectedElement}")

    var value = "${selectedElement?.name} "

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // textfield
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedElement?.name ?: "without category",
            onValueChange = { value = it },
            label = { Text(text = "without building") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.AdamantineBlue),
                unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
            )
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // menu items
            listOfElements.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text("${selectionOption.name}") },
                    onClick = {
                        selectedElement = selectionOption
                        expanded = false
                        onItemSelected(selectionOption.id)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun BaseImage() {
    Image(
        painter = painterResource(id = R.drawable.qr_code),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
            .clip(MaterialTheme.shapes.medium)
            .padding(top = 8.dp)
    )
}

@Composable
fun BaseButton(
    onClick: () -> Unit,
    colors: ButtonColors,
    text: String,
    enabled: Boolean
) {
    Button(
        onClick = { onClick() },
        colors = colors,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 8.dp)
            .size(height = 48.dp, width = 218.dp),
        enabled = enabled
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
private fun SnackBarToast(
    snackbarMessage: SnackbarMessage?, context: Context
) {
    snackbarMessage?.let { message ->
        Log.d("snack", "${message}")
        Snackbar(modifier = Modifier.padding(8.dp), actionOnNewLine = true, dismissAction = {
            TextButton(onClick = { SnackbarManager.clearSnackbarState() }) {
                Text(text = "Закрыть", color = colorResource(id = R.color.AdamantineBlue))
            }
        }) {
            Text(message.toMessage(context.resources), fontSize = 12.sp)
        }

    }
}