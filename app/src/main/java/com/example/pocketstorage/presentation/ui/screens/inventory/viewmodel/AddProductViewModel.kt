package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.presentation.ui.screens.building.BuildingState
import com.example.pocketstorage.presentation.ui.screens.building.BuildingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(BuildingState())
    val state = _state.asStateFlow()


    fun getBuildings() {
        viewModelScope.launch {
            getLocationsUseCase()
                .collect { locations ->
                    _state.update {
                        it.copy(locations = locations)
                    }
                }
        }
    }


}