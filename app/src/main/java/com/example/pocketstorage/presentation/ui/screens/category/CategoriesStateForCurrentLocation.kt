package com.example.pocketstorage.presentation.ui.screens.category

import com.example.pocketstorage.domain.model.Category

data class CategoriesStateForCurrentLocation(
    val currentLocationId: String = "",
    val existingCategoriesForCurrentLocation: List<Category> = emptyList(),
    val searchText: String = ""
)