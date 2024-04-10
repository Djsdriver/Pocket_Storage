package com.example.pocketstorage.presentation.ui.screens.inventory.stateui

import com.example.pocketstorage.domain.model.Inventory

data class ProductUIState(
    val name: String = "",
    val description: String = " ",
    val locationId: String = "",
    val categoryId: String = "",
    val pathToImage: String? = null,
    val products: List<Inventory> = emptyList(),
    val selectedIdBuilding: String = "",
    val data:String = "",
    val searchText : String = ""
)
