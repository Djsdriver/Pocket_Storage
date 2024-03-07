package com.example.pocketstorage.presentation.ui.screens.building

import com.example.pocketstorage.domain.model.Location

sealed class BuildingUiState{

    data class Loading(val searchText: String) : BuildingUiState()
    data class Success(
        val locations: List<Location> = emptyList(),
    ) : BuildingUiState() {
        fun isEmpty(): Boolean = locations.isEmpty()
    }
}