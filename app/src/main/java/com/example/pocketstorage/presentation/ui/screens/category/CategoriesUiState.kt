package com.example.pocketstorage.presentation.ui.screens.category

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory

sealed class CategoriesUiState {
    data object Loading : CategoriesUiState()
    data class Success(
        val categories: List<Category> = emptyList(),
        val inventoryList: List<Inventory?> = emptyList()
    ) : CategoriesUiState() {
        fun isEmpty() = categories.isEmpty()
    }
}