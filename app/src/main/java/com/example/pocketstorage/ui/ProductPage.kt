package com.example.pocketstorage.ui

import android.telecom.Call.Details
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocketstorage.R
import com.example.pocketstorage.ui.theme.PocketStorageTheme
import kotlinx.coroutines.launch

@Composable
fun ProductPage() {
    PocketStorageTheme {
        InfoProductInfo()
    }
}


@Composable
fun InfoProductInfo() {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun InfoProductInfoPreview() {
    PocketStorageTheme { // Обернуть в PocketStorageTheme
        ScaffoldWithTopBar()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Product")
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
    val blueColor = colorResource(id = R.color.blue)
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

    val pagerState = rememberPagerState(initialPage = tabIndex)
    
    LaunchedEffect(tabIndex){
        pagerState.animateScrollToPage(tabIndex)
    }

    LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress){
        if(!pagerState.isScrollInProgress){
            tabIndex=pagerState.currentPage
        }

    }

    Column {
        ScrollableTabRow(
            edgePadding = (0).dp,
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.blue),
            indicator = {

            },
            divider = {

            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = index==tabIndex,
                    onClick = {
                        tabIndex = index
                    },
                    selectedContentColor = colorResource(id = R.color.blue),
                    unselectedContentColor = colorResource(id = R.color.gray_tab)
                )
            }
        }

        HorizontalPager(pageCount = tabs.size, state = pagerState) { page ->
            when (page) {
                0 -> TabItem.DetailsScreen.screen.invoke()
                1 -> TabItem.LocationsScreen.screen.invoke()
                2 -> TabItem.QRScreen.screen.invoke()
            }
        }
    }
}




typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object DetailsScreen : TabItem("Details", { DetailsScreen() })
    object LocationsScreen : TabItem("Location", { LocationsScreen() })
    object QRScreen : TabItem("QR", { QRScreen() })
}


@Composable
fun QRScreen() {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "QR")
            }


}

@Composable
fun LocationsScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Location")
    }



}

@Composable
fun DetailsScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Details")
    }


}
