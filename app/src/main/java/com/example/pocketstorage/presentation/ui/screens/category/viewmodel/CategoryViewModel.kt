package com.example.pocketstorage.presentation.ui.screens.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.doesMatchSearchQuery
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.presentation.ui.screens.category.CategoriesStateForCurrentLocation
import com.example.pocketstorage.presentation.ui.screens.category.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getLocationIdFromDataStorageUseCase: GetLocationIdFromDataStorageUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase
) : ViewModel() {

    private val _categoriesState = MutableStateFlow(CategoriesStateForCurrentLocation())
    val categoriesState=_categoriesState.asStateFlow()

    private val _uiState: MutableStateFlow<CategoriesUiState> = MutableStateFlow(CategoriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentLocationId()

            categoriesState.collect { state ->
                if (state != null) {
                    getAllCategoriesByLocationId(state.currentLocationId)
                }
                if (state != null) {
                    _uiState.update {
                        if (state.searchText.isNotBlank()) {
                            CategoriesUiState.Success(
                                categories = state.existingCategoriesForCurrentLocation.filter {
                                    it.doesMatchSearchQuery(
                                        state.searchText
                                    )
                                }
                            )
                        } else {
                            CategoriesUiState.Success(
                                categories = state.existingCategoriesForCurrentLocation
                            )
                        }
                    }
                }

            }
        }
    }

    fun onSearchTextChange(text: String) {
        viewModelScope.launch {
            _categoriesState.update {
                it.copy(searchText = text)
            }
        }
    }

    fun saveCategoryOnLocalStorage(category: Category, onSuccess: ()-> Unit) {
        viewModelScope.launch {
            insertCategoryUseCase(category)
            onSuccess()
        }

    }

    private fun getCurrentLocationId() {
        viewModelScope.launch {
            getLocationIdFromDataStorageUseCase.invoke().collect { currentLocationId ->
                if (currentLocationId != null) {
                    _categoriesState.update {
                        it.copy(
                            currentLocationId = currentLocationId
                        )
                    }
                }
            }
        }
    }

    fun getAllCategoriesByLocationId(locationId: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            getCategoriesByBuildingIdUseCase.invoke(locationId).collect { categories->
               _categoriesState.update {
                   it.copy(
                       existingCategoriesForCurrentLocation = categories
                   )
               }
            }
        }
    }
}

