package com.example.pocketstorage.presentation.ui.screens.inventory.stateui

import com.example.pocketstorage.domain.model.Inventory

data class ProductPageUiState(
    val idProduct: String = "",
    val name: String = "",
    val idLocation: String = "",
    val idCategory: String = "",
    val description: String = "",
    val nameCategory: String = "",
    val nameBuilding: String = "",
    val pathToImage : String = "",
)
