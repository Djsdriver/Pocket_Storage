package com.example.pocketstorage.presentation.ui.screens.inventory

import android.os.Environment
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pocketstorage.R
import com.example.pocketstorage.components.EditorNameComponent
import com.example.pocketstorage.components.SnackBarToast
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductPageEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.ProductPageViewModel
import com.example.pocketstorage.ui.theme.PocketStorageTheme
import com.example.pocketstorage.utils.SnackbarManager
import java.io.File

@Composable
fun ProductPage(
    onClick: () -> Unit,
    id: String,
    viewModel: ProductPageViewModel,
    onEvent: (ProductPageEvent) -> Unit
) {
    PocketStorageTheme {
        InfoProductInfo(onClick, id, viewModel, onEvent)
    }
}


@Composable
fun InfoProductInfo(
    onClick: () -> Unit,
    id: String,
    viewModel: ProductPageViewModel,
    onEvent: (ProductPageEvent) -> Unit
) {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBarProductPage(onClick, id, viewModel, onEvent)
    }
}

//@Preview(showBackground = true)
@Composable
fun InfoProductInfoPreview(
    onClick: () -> Unit,
    id: String,
    viewModel: ProductPageViewModel,
    onEvent: (ProductPageEvent) -> Unit
) {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBarProductPage(onClick, id, viewModel, onEvent)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarProductPage(
    onClick: () -> Unit,
    id: String,
    viewModel: ProductPageViewModel,
    onEvent: (ProductPageEvent) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarMessage by SnackbarManager.snackbarMessages.collectAsState()
    
    val editorName = remember {
        mutableStateOf(false)
    }
    val productName = remember { mutableStateOf(state.name) }

    LaunchedEffect(Unit) {
        onEvent(ProductPageEvent.ShowInfoProduct(id))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Product")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClick()
                    }) {
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = state.name, fontSize = 20.sp)
                    IconButton(onClick = { editorName.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_square),
                            contentDescription = "edit"
                        )
                    }
                }
                DashedBorderWithImage(viewModel)
                TabScreen(id, viewModel, onEvent)
            }
        }
    )
    
    if(editorName.value){
        val inventoryId = state.idProduct
        EditorNameComponent(
            productName = productName,
            onDismiss = { editorName.value = false}
        ) { newName ->
            if (newName.isNotEmpty()){
                onEvent(ProductPageEvent.UpdateNameProduct(inventoryId,newName))
                editorName.value = false
            }
        }
    }

    SnackBarToast(snackbarMessage = snackbarMessage, context = LocalContext.current)

}

@Composable
fun DashedBorderWithImage(viewModel: ProductPageViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_album")
    val imageFile = File(filePath, state.pathToImage)
    val placeholder = painterResource(id = R.drawable.add_photo_alternate)
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f)
    )
    val blueColor = colorResource(id = R.color.AdamantineBlue)
    Box(
        Modifier
            .fillMaxWidth()
            .height(240.dp)
            .run {
                if (state.pathToImage.isNotEmpty()) {
                    this
                } else {
                    drawBehind {
                        drawRoundRect(
                            cornerRadius = CornerRadius(8f, 8f),
                            color = blueColor,
                            style = stroke
                        )
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageFile.exists()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageFile)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = placeholder,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(id: String, viewModel: ProductPageViewModel, onEvent: (ProductPageEvent) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Details", "Location", "QR")


    val state = rememberPagerState(pageCount = { 3 })

    LaunchedEffect(tabIndex) {
        state.animateScrollToPage(tabIndex, animationSpec = spring(stiffness = Spring.StiffnessLow))
    }

    LaunchedEffect(state.currentPage, state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            tabIndex = state.currentPage
        }

    }


    Column {
        ScrollableTabRow(
            edgePadding = (0).dp,
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.RetroBlue),
            indicator = {

            },
            divider = {

            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = index == tabIndex,
                    onClick = {
                        tabIndex = index
                    },
                    selectedContentColor = colorResource(id = R.color.RetroBlue),
                    unselectedContentColor = colorResource(id = R.color.SpanishGrey)
                )
            }
        }

        HorizontalPager(
            verticalAlignment = Alignment.Top,
            state = state,
            beyondBoundsPageCount = 3
        ) {
            when (it) {
                0 -> TabItem.DetailsScreen1(
                    id = id,
                    viewModel = viewModel)
                    .screen.invoke()
                1 -> TabItem.LocationsScreen1(
                    onEvent = onEvent,
                    viewModel = viewModel)
                    .screen.invoke()
                2 -> TabItem.QRScreen1(
                    content = id,
                    onEvent = onEvent,
                    viewModel = viewModel
                ).screen.invoke()
            }
        }
    }
}


typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var screen: ComposableFun) {
    data class DetailsScreen1(val id: String, val viewModel: ProductPageViewModel) :
        TabItem({ DetailsScreen(id, viewModel) })

    data class LocationsScreen1(val onEvent: (ProductPageEvent) -> Unit, val viewModel: ProductPageViewModel) :
        TabItem({ LocationsScreen(onEvent = onEvent, viewModel = viewModel) })

    data class QRScreen1(
        val content: String,
        val onEvent: (ProductPageEvent) -> Unit,
        val viewModel: ProductPageViewModel
    ) : TabItem({ QRScreen(content = content, onEvent = onEvent, viewModel = viewModel) })
}


@Composable
fun QRScreen(
    content: String,
    onEvent: (ProductPageEvent) -> Unit,
    viewModel: ProductPageViewModel
) {
    LaunchedEffect(Unit) {
        onEvent(ProductPageEvent.GenerationQrCode(content))
    }
    val state by viewModel.state.collectAsState()


    val context = LocalContext.current // Получение доступа к контексту
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (state.generatedBitmap != null) {
                Image(
                    bitmap = state.generatedBitmap!!.asImageBitmap(),
                    contentDescription = "qr",
                    modifier = Modifier
                        .size(128.dp)
                        .clickable {
                            onEvent(ProductPageEvent.GenerationQrCode(content = content))
                        }
                )
            }

            Row {
                IconButton(modifier = Modifier.padding(end = 40.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.print),
                        contentDescription = "print"
                    )
                }
                IconButton(modifier = Modifier.padding(end = 40.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.download_qr),
                        contentDescription = "download_qr"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.visibility_off),
                        contentDescription = "visibility_off"
                    )
                }
            }
        }

    }

}

//@Preview(showBackground = true)
@Composable
fun LocationsScreen(onEvent: (ProductPageEvent) -> Unit, viewModel: ProductPageViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        onEvent(ProductPageEvent.ShowListLocation)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonSaveProductPage {
                //click
            }

            LazyColumn(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 10.dp)
                    .background(Color.White)
            ) {
                items(state.listLocation) { listLocation ->
                    ListRowForLocationProduct(location = listLocation, viewModel = viewModel)
                }
            }
        }

    }
}


@Composable
fun ButtonSaveProductPage(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.RetroBlue)),
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "supply")
        Text(text = "Применить", color = Color.White)
    }
}

@Composable
fun DetailsScreen(id: String, viewModel: ProductPageViewModel) {
    val state by viewModel.state.collectAsState()
    Log.d("stateProduct", "${state.nameBuilding}")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Column {
            Row {
                Text(text = "Id:", fontSize = 12.sp)
                Text(text = id, fontSize = 12.sp)
            }
            Row {
                Text(text = "Category:", fontSize = 12.sp)
                Text(text = state.nameCategory, fontSize = 12.sp)
            }
            Row {
                Text(text = "Description: ", fontSize = 12.sp)
                Text(
                    text = state.description,
                    fontSize = 12.sp
                )
            }
            Row {
                Text(text = "Location: ", fontSize = 12.sp)
                Text(text = state.nameBuilding, fontSize = 12.sp)
            }
        }

    }


}

@Composable
fun ListRowForLocationProduct(location: Location, viewModel: ProductPageViewModel) {
    val state by viewModel.state.collectAsState()

    val animatedColorState = animateColorAsState(
        targetValue = if (location.id == state.idLocation) colorResource(id = R.color.SpanishGrey) else colorResource(id = R.color.AdamantineBlue),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .height(IntrinsicSize.Min)
            .wrapContentHeight()
            .fillMaxWidth()
            .background(animatedColorState.value),
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp, top = 9.dp, bottom = 2.dp, end = 70.dp),
            text = location.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, bottom = 2.dp, end = 70.dp),
            text = location.index,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, bottom = 2.dp, end = 70.dp),
            text = location.address,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}
