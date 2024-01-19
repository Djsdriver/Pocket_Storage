package com.example.pocketstorage.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Inventory : BottomBarScreen(
        route = "INVENTORY",
        title = "INVENTORY",
        icon = Icons.Default.Home
    )

    data object Category : BottomBarScreen(
        route = "CATEGORY",
        title = "CATEGORY",
        icon = Icons.Default.Person
    )

    data object Building : BottomBarScreen(
        route = "BUILDING",
        title = "BUILDING",
        icon = Icons.Default.Person
    )

    data object Settings : BottomBarScreen(
        route = "SETTINGS",
        title = "SETTINGS",
        icon = Icons.Default.Settings
    )
}