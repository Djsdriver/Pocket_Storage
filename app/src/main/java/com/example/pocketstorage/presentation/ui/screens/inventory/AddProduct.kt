package com.example.pocketstorage.presentation.ui.screens.inventory

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pocketstorage.R

@Composable
fun CreateProduct(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onGenerateQRClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    AddProductScreen(
        onBackArrowClick = onBackArrowClick,
        onAddPictureClick = onAddPictureClick,
        onGenerateQRClick = onGenerateQRClick,
        onSaveClick = onSaveClick
    )
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen(
        onBackArrowClick = {},
        onAddPictureClick = {},
        onGenerateQRClick = {},
        onSaveClick = {}
    )
}

@Composable
fun AddProductScreen(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onGenerateQRClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    ScaffoldBase(
        onBackArrowClick = onBackArrowClick,
        onAddPictureClick = onAddPictureClick,
        onGenerateQRClick = onGenerateQRClick,
        onSaveClick = onSaveClick
    )
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ScaffoldBase(
    onBackArrowClick: () -> Unit,
    onAddPictureClick: () -> Unit,
    onGenerateQRClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onBackArrowClick = onBackArrowClick)
        },
        content = { paddingValues ->
            BaseContent(
                paddingValues = paddingValues,
                onAddPictureClick = onAddPictureClick,
                onGenerateQRClick = onGenerateQRClick,
                onSaveClick = onSaveClick
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackArrowClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Create Product")
        },
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        }
    )
}

@Composable
fun BaseContent(
    paddingValues: PaddingValues,
    onAddPictureClick: () -> Unit,
    onGenerateQRClick: () -> Unit,
    onSaveClick: () -> Unit,
    topPadding: Dp = 8.dp
) {

    var imageIsVisible by remember {
        mutableStateOf(false)
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
        AddPictureCard(onClick = onAddPictureClick)
        BaseTextField(
            textHint = "name",
            colorHint = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth()
        )
        BaseTextField(
            textHint = "description (optional)",
            colorHint = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth()
                .height(112.dp)
        )
        BaseDropdownMenu(
            listOfElements = listOf(
                "without category",
                "computers",
                "printers",
                "telephones"
            ),
            modifier = Modifier.padding(top = topPadding)
        )
        BaseDropdownMenu(
            listOfElements = listOf(
                "without building",
                "building 1",
                "building 2",
                "building 3"
            ),
            modifier = Modifier.padding(top = topPadding)
        )
        if (imageIsVisible) {
            BaseImage()
        }
        BaseButton(
            onClick = {
                imageIsVisible = !imageIsVisible
                onGenerateQRClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.SmoothPurple)
            ),
            text = "Generate QR"
        )
        BaseButton(
            onClick = {
                onSaveClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.RetroBlue)
            ),
            text = "Save"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPictureCard(onClick: () -> Unit) {

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

    val launcherGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val launcherCamera = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { photoBitmap ->
        bitmap.value = photoBitmap
        imageUri = null

        pathToLoadingPicture = photoBitmap.toString()
        color.value = Color.Transparent
    }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value= ImageDecoder.decodeBitmap(source)
        }

        pathToLoadingPicture = it.toString()
        color.value = Color.Transparent
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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (bitmap.value != null) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.add_photo_alternate),
                    contentDescription = "Add Photo",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color(R.color.SpanishGrey)
                )
            }
            pathToLoadingPicture?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun BaseTextField(
    textHint: String,
    colorHint: Color,
    modifier: Modifier
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
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
fun BaseDropdownMenu(listOfElements: List<String>, modifier: Modifier) {

    var expanded by remember { mutableStateOf(false) }
    var selectedElement by remember { mutableStateOf(listOfElements[0]) }

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        // textfield
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedElement,
            onValueChange = {},
            label = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.AdamantineBlue),
                unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
            ),
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            // menu items
            listOfElements.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedElement = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
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
) {
    Button(
        onClick = { onClick() },
        colors = colors,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 8.dp)
            .size(height = 48.dp, width = 218.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}