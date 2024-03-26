package com.example.pocketstorage.presentation.ui.screens.category

import com.example.pocketstorage.domain.model.Category

sealed class CategoriesUiState {
    data object Loading : CategoriesUiState()
    data class Success(
        val categories: List<Category> = emptyList(),
    ) : CategoriesUiState() {
        fun isEmpty() = categories.isEmpty()
    }
}