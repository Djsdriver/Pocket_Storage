package com.example.pocketstorage.ui

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.pocketstorage.R
import com.example.pocketstorage.ui.theme.PocketStorageTheme

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

@Preview(showBackground = true)
@Composable
fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Details", "Location", "QR")

    Column() {
        ScrollableTabRow(
            edgePadding = (0).dp,
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.blue),
            indicator = {

            },
            divider = {

            },

        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    selectedContentColor = colorResource(id = R.color.blue),
                    unselectedContentColor = colorResource(id = R.color.gray_tab)
                )
            }
        }
        when (tabIndex) {
            0 -> HomeScreen()
            1 -> AboutScreen()
            2 -> SettingsScreen()
        }
    }
}

@Composable
fun SettingsScreen() {
    Text(text = "QR")
}

@Composable
fun AboutScreen() {
    Text(text = "Location")

}

@Composable
fun HomeScreen() {
    Text(text = "Details")

}
