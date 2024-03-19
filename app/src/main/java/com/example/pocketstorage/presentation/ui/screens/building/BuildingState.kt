package com.example.pocketstorage.presentation.ui.screens.building

import com.example.pocketstorage.domain.model.Location

data class BuildingState(
    val searchText: String = "",
    var isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val resultLocations: List<Location> = emptyList(),
    val empty: Boolean = false,
    val isSelected: String = ""
)