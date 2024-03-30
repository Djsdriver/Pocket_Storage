package com.example.pocketstorage.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pocketstorage.presentation.ui.screens.building.Building
import com.example.pocketstorage.presentation.ui.screens.building.CreateBuilding
import com.example.pocketstorage.presentation.ui.screens.building.viewmodel.CreateBuildingViewModel
import com.example.pocketstorage.presentation.ui.screens.category.Category
import com.example.pocketstorage.presentation.ui.screens.category.viewmodel.CategoryViewModel
import com.example.pocketstorage.presentation.ui.screens.inventory.CreateProduct
import com.example.pocketstorage.presentation.ui.screens.inventory.InventoryScreen
import com.example.pocketstorage.presentation.ui.screens.inventory.ProductPage
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.AddProductViewModel


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
                },
                {
                    navController.navigate(Graph.AUTHENTICATION)
                }

            )
        }
        composable(route = BottomBarScreen.Category.route) {
            val viewModel = hiltViewModel<CategoryViewModel>()
            Category(viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Building.route) {
            Building {
                navController.navigate(BuildingScreenState.CreateBuilding.route)
            }
        }
        inventoryNavGraph(navController = navController)
        buildingNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.inventoryNavGraph(navController: NavHostController) {
    composable(route = InventoryScreenState.CreateProduct.route) {
        val viewModel = hiltViewModel<AddProductViewModel>()
        CreateProduct (
            onBackArrowClick = { navController.navigateUp() },
            onAddPictureClick = {},
            onGenerateQRClick = {},
            onSaveClick = {},
            onEvent = viewModel::event
        )
    }
    composable(route = InventoryScreenState.InfoProduct.route) {
        ProductPage {
            navController.navigateUp()
        }
    }
}

fun NavGraphBuilder.buildingNavGraph(navController: NavHostController) {
    composable(route = BuildingScreenState.CreateBuilding.route) {
        val viewModelCreateBuilding = hiltViewModel<CreateBuildingViewModel>()
        CreateBuilding(viewModelCreateBuilding,viewModelCreateBuilding::event) {
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
