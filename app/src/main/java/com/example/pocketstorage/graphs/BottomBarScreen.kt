package com.example.pocketstorage.graphs

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.pocketstorage.R

sealed class BottomBarScreen(
    val route: String,
    val icon: @Composable () -> Unit
) {
    data object Inventory : BottomBarScreen(
        route = "INVENTORY",
        icon = { Icon(painter = painterResource(id = R.drawable.inventory_2), contentDescription ="" ) }
    )

    data object Category : BottomBarScreen(
        route = "CATEGORY",
        icon = { Icon(painter = painterResource(id = R.drawable.category), contentDescription ="" ) }
    )

    data object Building : BottomBarScreen(
        route = "BUILDING",
        icon = { Icon(painter = painterResource(id = R.drawable.building), contentDescription ="" ) }
    )

    data object Settings : BottomBarScreen(
        route = "SETTINGS",
        icon = { Icon(painter = painterResource(id = R.drawable.settings), contentDescription ="" ) }
    )
}