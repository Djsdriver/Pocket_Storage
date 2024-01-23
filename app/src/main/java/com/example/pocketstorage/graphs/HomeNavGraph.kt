package com.example.pocketstorage.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pocketstorage.ui.screens.Building
import com.example.pocketstorage.ui.screens.Category
import com.example.pocketstorage.ui.screens.CreateBuilding
import com.example.pocketstorage.ui.screens.CreateProduct
import com.example.pocketstorage.ui.screens.InventoryScreen
import com.example.pocketstorage.ui.screens.ProductPage


@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Inventory.route
    ) {
        composable(route = BottomBarScreen.Inventory.route) {
            InventoryScreen(
                {
                    navController.navigate(InventoryScreenState.InfoProduct.route)
                },
                {
                    navController.navigate(InventoryScreenState.CreateProduct.route)
                }
            )
        }
        composable(route = BottomBarScreen.Category.route) {
            Category()
        }
        composable(route = BottomBarScreen.Building.route) {
            Building {
                navController.navigate(BuildingScreenState.CreateBuilding.route)
            }
        }
        inventoryNavGraph(navController = navController)
        buildingNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.inventoryNavGraph(navController: NavHostController) {
    composable(route = InventoryScreenState.CreateProduct.route) {
        CreateProduct {
            navController.navigateUp()
        }
    }
    composable(route = InventoryScreenState.InfoProduct.route) {
        ProductPage {
            navController.navigateUp()
        }
    }
}

fun NavGraphBuilder.buildingNavGraph(navController: NavHostController) {
    composable(route = BuildingScreenState.CreateBuilding.route) {
        CreateBuilding {
            navController.navigateUp()
        }
    }

}

sealed class InventoryScreenState(val route: String) {
    data object CreateProduct : InventoryScreenState(route = "CREATE_PRODUCT")
    data object InfoProduct : InventoryScreenState(route = "INFO_PRODUCT")
}

sealed class BuildingScreenState(val route: String) {
    data object CreateBuilding : BuildingScreenState(route = "CREATE_BUILDING")
}
