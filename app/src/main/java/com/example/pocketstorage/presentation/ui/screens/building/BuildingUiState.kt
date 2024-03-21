package com.example.pocketstorage.presentation.ui.screens.building

import com.example.pocketstorage.domain.model.Location

sealed class BuildingUiState{
    data object Loading : BuildingUiState()
    data class Success(
        val locations: List<Location> = emptyList(),
    ) : BuildingUiState() {
        fun isEmpty() = locations.isEmpty()
    }
}