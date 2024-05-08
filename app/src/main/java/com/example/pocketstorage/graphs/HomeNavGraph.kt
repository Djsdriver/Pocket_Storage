package com.example.pocketstorage.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pocketstorage.presentation.ui.screens.building.Building
import com.example.pocketstorage.presentation.ui.screens.building.CreateBuilding
import com.example.pocketstorage.presentation.ui.screens.building.viewmodel.CreateBuildingViewModel
import com.example.pocketstorage.presentation.ui.screens.category.Category
import com.example.pocketstorage.presentation.ui.screens.category.viewmodel.CategoryViewModel
import com.example.pocketstorage.presentation.ui.screens.inventory.CreateProduct
import com.example.pocketstorage.presentation.ui.screens.inventory.InventoryScreen
import com.example.pocketstorage.presentation.ui.screens.inventory.ProductPage
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.AddProductViewModel
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.InventoryViewModel
import com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel.ProductPageViewModel


@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Inventory.route
    ) {
        composable(route = BottomBarScreen.Inventory.route) {
            val viewModel = hiltViewModel<InventoryViewModel>()
            InventoryScreen(
                {idProduct->
                    navController.navigate(InventoryScreenState.InfoProduct(idProduct).route)
                },
                {
                    navController.navigate(InventoryScreenState.CreateProduct.route)
                },
                {
                    navController.navigate(Graph.AUTHENTICATION)
                },
                {idProductFromQrCode->
                    navController.navigate(InventoryScreenState.InfoProduct(idProductFromQrCode).route)
                },
                viewModel::event
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
        CreateProduct(
            onBackArrowClick = { navController.navigateUp() },
            onAddPictureClick = {},
            onSaveClick = {},
            onEvent = viewModel::event
        )
    }
    composable(
        route = InventoryScreenState.InfoProduct("{id}").route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) {
        val viewModel = hiltViewModel<ProductPageViewModel>()
        val id = it.arguments?.getString("id") ?: ""
        ProductPage(
            {
                navController.navigateUp()
            },
            id = id,
            viewModel = viewModel,
            onEvent = viewModel::event
        )
    }
}

fun NavGraphBuilder.buildingNavGraph(navController: NavHostController) {
    composable(route = BuildingScreenState.CreateBuilding.route) {
        val viewModelCreateBuilding = hiltViewModel<CreateBuildingViewModel>()
        CreateBuilding(viewModelCreateBuilding, viewModelCreateBuilding::event) {
            navController.navigateUp()
        }
    }
}

sealed class InventoryScreenState(val route: String) {
    data object CreateProduct : InventoryScreenState(route = "CREATE_PRODUCT")
    data class InfoProduct(val id: String) : InventoryScreenState(route = "INFO_PRODUCT/$id")

}

sealed class BuildingScreenState(val route: String) {
    data object CreateBuilding : BuildingScreenState(route = "CREATE_BUILDING")
}
