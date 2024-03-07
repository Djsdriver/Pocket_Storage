package com.example.pocketstorage.presentation.ui.screens.building.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.presentation.ui.screens.building.BuildingState
import com.example.pocketstorage.presentation.ui.screens.building.BuildingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BuildingState())
    val state = _state.asStateFlow()


    private val _uiState: MutableStateFlow<BuildingUiState> =
        MutableStateFlow(BuildingUiState.Loading(""))

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            state.collect { state ->
                _uiState.update {
                    if (state.isLoading || state.isSearching) {
                        BuildingUiState.Loading(
                            searchText = state.searchText
                        )
                    }
                    if (state.searchText.isNotBlank()) {
                        BuildingUiState.Success(
                            locations = state.locations.filter {
                                it.doesMatchSearchQuery(
                                    state.searchText
                                )
                            }
                        )
                    } else {
                        BuildingUiState.Success(
                            locations = state.locations
                        )
                    }
                }

            }
        }
    }

    fun refreshLocations() {
        viewModelScope.launch {
            getLocationsUseCase().onStart {
                _state.update { it.copy(isLoading = true) }
            }
                .collect { locations ->
                    _state.update {
                        it.copy(isLoading = false, locations = locations)
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(searchText = text)
            }
        }
    }
}

