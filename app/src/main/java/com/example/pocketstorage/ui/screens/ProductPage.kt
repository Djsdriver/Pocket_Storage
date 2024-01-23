package com.example.pocketstorage.ui.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocketstorage.R
import com.example.pocketstorage.ui.theme.PocketStorageTheme

@Composable
fun ProductPage(onClick: () -> Unit) {
    PocketStorageTheme {
        InfoProductInfo(onClick)
    }
}


@Composable
fun InfoProductInfo(onClick: () -> Unit) {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBarProductPage(onClick)
    }
}

//@Preview(showBackground = true)
@Composable
fun InfoProductInfoPreview(onClick: () -> Unit) {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBarProductPage(onClick)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarProductPage(onClick: () -> Unit) {
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
                    Text(text = "Samsung UR55", fontSize = 20.sp)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_square),
                            contentDescription = "edit"
                        )
                    }
                }
                DashedBorderWithImage()
                TabScreen()
            }
        }
    )

}

@Composable
fun DashedBorderWithImage() {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f)
    )
    val blueColor = colorResource(id = R.color.AdamantineBlue)
    Box(
        Modifier
            .fillMaxWidth()
            .height(240.dp)
            .drawBehind {
                drawRoundRect(
                    cornerRadius = CornerRadius(8f, 8f),
                    color = blueColor,
                    style = stroke
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_photo_alternate),
            contentDescription = "add"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Details", "Location", "QR")


    val state = rememberPagerState(pageCount = { 3 })

    LaunchedEffect(tabIndex) {
        state.animateScrollToPage(tabIndex)
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

        //старая версия не поддерживается больше
        /* HorizontalPager(pageCount = tabs.size, state = pagerState) { page ->
             when (page) {
                 0 -> TabItem.DetailsScreen.screen.invoke()
                 1 -> TabItem.LocationsScreen.screen.invoke()
                 2 -> TabItem.QRScreen.screen.invoke()
             }
         }*/

        HorizontalPager(state = state) {
            when (it) {
                0 -> TabItem.DetailsScreen.screen.invoke()
                1 -> TabItem.LocationsScreen.screen.invoke()
                2 -> TabItem.QRScreen.screen.invoke()
            }
        }
    }
}


typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var screen: ComposableFun) {
    object DetailsScreen : TabItem({ DetailsScreen() })
    object LocationsScreen : TabItem({ LocationsScreen() })
    object QRScreen : TabItem({ QRScreen() })
}


@Composable
fun QRScreen() {
    val context = LocalContext.current // Получение доступа к контексту
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.qr_code_for_mobile),
                contentDescription = "qr",
                modifier = Modifier
                    .size(128.dp)
                    .clickable {
                        Toast
                            .makeText(context, "Click", Toast.LENGTH_LONG)
                            .show()
                    }
            )
            Row() {
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

@Preview(showBackground = true)
@Composable
fun LocationsScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonSaveProductPage {
//click
            }

            //recycler
            val list = mutableListOf<ItemSchoolModel>()
            list.add(
                ItemSchoolModel(
                    "ГБОУ Школа №1500",
                    "MSK-1",
                    "Skornyazhnyy Pereulok, 3, Moscow",
                    "Located at:\nRoom 202: 6 units \nRoom 211: 3 units",
                    "Total count: 9"
                )
            )
            list.add(
                ItemSchoolModel(
                    "ГБОУ Школа №1500",
                    "MSK-1",
                    "Skornyazhnyy Pereulok, 3, Moscow",
                    "Located at:Room 202: 6 units \n Room 211: 3 units",
                    "Total count: 9"
                )
            )
            list.add(
                ItemSchoolModel(
                    "ГБОУ Школа №1500",
                    "MSK-1",
                    "Skornyazhnyy Pereulok, 3, Moscow",
                    "Located at:Room 202: 6 units \n Room 211: 3 units",
                    "Total count: 9"
                )
            )
            list.add(
                ItemSchoolModel(
                    "ГБОУ Школа №1500",
                    "MSK-1",
                    "Skornyazhnyy Pereulok, 3, Moscow",
                    "Located at:Room 202: 6 units \n Room 211: 3 units",
                    "Total count: 9"
                )
            )
            list.add(
                ItemSchoolModel(
                    "ГБОУ Школа №1500",
                    "MSK-1",
                    "Skornyazhnyy Pereulok, 3, Moscow",
                    "Located at:Room 202: 6 units \n Room 211: 3 units",
                    "Total count: 9"
                )
            )

            LazyColumn(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 10.dp)
                    .background(Color.White)
            ) {
                items(list) { model ->
                    ListRowForLocationProduct(model = model)
                }
            }
        }


    }
}


@Composable
fun ButtonSaveProductPage(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.purple_500)),
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "supply")
        Text(text = "Supply", color = Color.White)
    }
}

@Composable
fun DetailsScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            Row {
                Text(text = "Id:", fontSize = 12.sp)
                Text(text = "123", fontSize = 12.sp)
            }
            Row {
                Text(text = "Category:", fontSize = 12.sp)
                Text(text = "Monitor", fontSize = 12.sp)
            }
            Row {
                Text(text = "Description: ", fontSize = 12.sp)
                Text(
                    text = "Multiline text multiline text Multiline textMultiline textMultiline text Multiline text Multiline text Multiline text",
                    fontSize = 12.sp
                )
            }
            Row {
                Text(text = "Inventory number: A111", fontSize = 12.sp)
                Text(text = "A111", fontSize = 12.sp)
            }
        }

    }


}

@Composable
fun ListRowForLocationProduct(model: ItemSchoolModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .wrapContentHeight()
            .fillMaxWidth()
            .background(colorResource(id = R.color.RetroBlue)),
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp, top = 9.dp, bottom = 2.dp, end = 70.dp),
            text = model.nameSchool,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, bottom = 2.dp, end = 70.dp),
            text = model.region,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, bottom = 2.dp, end = 70.dp),
            text = model.locatedAt,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, bottom = 2.dp, end = 70.dp),
            text = model.totalCount,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

data class ItemSchoolModel(
    val nameSchool: String,
    val region: String,
    val adress: String,
    val locatedAt: String,
    val totalCount: String,
)
