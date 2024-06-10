package com.example.pocketstorage.presentation.ui.screens.category

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory

data class CategoriesStateForCurrentLocation(
    val currentLocationId: String = "",
    val currentCategoryId: String = "",
    val existingCategoriesForCurrentLocation: List<Category> = emptyList(),
    val searchText: String = "",
    val inventoryList: List<Inventory> = emptyList(),
    val expandedCategory: List<String> = emptyList(),
    val expandedCurrentCategory: Boolean = false,
    val activeCategory: String = "",
    val expandedIcons: Map<String, Boolean> = emptyMap(),
    val allListInventory:  List<Inventory> = emptyList()
)