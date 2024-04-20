package com.example.pocketstorage.presentation.ui.screens.inventory

import android.net.Uri
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Location

data class InventoryUiState(
    val name: String = "",
    val description: String = " ",
    val locationId: String = "",
    val categoryId: String = "",
    val pathToImage: String? = null,
    val locations: List<Location> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedIdBuilding: String = "",
)