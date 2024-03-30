package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.domain.usecase.db.InsertInventoryUseCase
import com.example.pocketstorage.presentation.ui.screens.building.BuildingState
import com.example.pocketstorage.presentation.ui.screens.building.BuildingUiState
import com.example.pocketstorage.presentation.ui.screens.building.CreateBuildingEvent
import com.example.pocketstorage.presentation.ui.screens.category.CategoriesStateForCurrentLocation
import com.example.pocketstorage.presentation.ui.screens.inventory.CreateProductEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase,
    private val insertInventoryUseCase: InsertInventoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BuildingState())
    val state = _state.asStateFlow()

    private val _categoriesState = MutableStateFlow(CategoriesStateForCurrentLocation())
    val categoriesState = _categoriesState.asStateFlow()

    private val _stateMvi = MutableStateFlow(Inventory())
    val stateMvi = _stateMvi.asStateFlow()


    fun event(createProductEvent: CreateProductEvent) {
        when (createProductEvent) {
            is CreateProductEvent.CreateBuilding -> {
                val name = stateMvi.value.name
                val description = stateMvi.value.description
                val locationId = stateMvi.value.locationId
                val categoryId = stateMvi.value.categoryId
                val pathToImage = stateMvi.value.pathToImage

                if (name.isBlank() || description.isBlank()) {
                    return
                }

                val product = Inventory(
                    name = name,
                    description = description,
                    locationId = locationId,
                    categoryId = categoryId,
                    pathToImage = pathToImage
                )
                viewModelScope.launch {
                    insertInventoryUseCase.invoke(product)
                    _stateMvi.update {
                        it.copy(
                            name = "",
                            description = "",
                            locationId = "",
                            categoryId = "",
                            pathToImage = ""
                        )
                    }
                }



            }

            is CreateProductEvent.SetNameProduct -> {
                _stateMvi.update {
                    it.copy(
                        name = createProductEvent.nameProduct
                    )
                }

            }

            is CreateProductEvent.SetDescription -> {
                _stateMvi.update {
                    it.copy(
                        description = createProductEvent.description
                    )
                }
            }

            is CreateProductEvent.SetCategoryId -> {
                _stateMvi.update {
                    it.copy(
                        categoryId = createProductEvent.categoryId
                    )
                }
            }

            is CreateProductEvent.SetLocationId -> {
                _stateMvi.update {
                    it.copy(
                        locationId = createProductEvent.locationId
                    )
                }
            }

            is CreateProductEvent.SetPathToImage -> {
                _stateMvi.update {
                    it.copy(
                        pathToImage = createProductEvent.pathToImage
                    )
                }
            }

            else -> {}
        }
    }


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


    fun getCategoriesByBuilding(buildingId: String) {
        viewModelScope.launch {
            getCategoriesByBuildingIdUseCase.invoke(buildingId)
                .collect { category ->
                    _categoriesState.update {
                        it.copy(
                            existingCategoriesForCurrentLocation = category
                        )
                    }
                }
        }
    }


}