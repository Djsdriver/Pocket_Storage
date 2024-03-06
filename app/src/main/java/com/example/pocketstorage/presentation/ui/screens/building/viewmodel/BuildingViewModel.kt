package com.example.pocketstorage.presentation.ui.screens.building.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _locations = MutableStateFlow(emptyList<Location>())
    val locations = _locations.asStateFlow()


    fun refreshLocations() {
        viewModelScope.launch {
            getLocationsUseCase().collect { locations ->
                _locations.value = locations
            }
        }
    }

    val filteredLocations = _searchText
        .debounce(1000L)
        .onEach { _isSearching.value = true }
        .map { text ->
            if (text.isBlank()) {
                _locations.value
            } else {
                delay(2000L)
                _locations.value.filter { it.doesMatchSearchQuery(text) }
            }
        }
        .onEach { _isSearching.value = false }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _locations.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}


