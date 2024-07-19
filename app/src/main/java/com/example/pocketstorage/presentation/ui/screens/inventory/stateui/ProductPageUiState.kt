package com.example.pocketstorage.presentation.ui.screens.inventory.stateui

import android.graphics.Bitmap
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location

data class ProductPageUiState(
    val idProduct: String = "",
    val name: String = "",
    val idLocation: String = "",
    val idCategory: String = "",
    val description: String = "",
    val nameCategory: String = "",
    val nameBuilding: String = "",
    val pathToImage : String = "",
    val address : String = "",
    val selectedBuildingIdForTransfer : String = "",
    val selectedCategoryIdForTransfer : String = "",
    val generatedBitmap: Bitmap? = null,
    val listLocation: List<Location> = emptyList(),
    val listCategory: List<Category> = emptyList(),
)
