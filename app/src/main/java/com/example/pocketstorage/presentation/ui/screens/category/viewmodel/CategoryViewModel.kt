package com.example.pocketstorage.presentation.ui.screens.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.doesMatchSearchQuery
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByCategoryIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.presentation.ui.screens.category.CategoriesStateForCurrentLocation
import com.example.pocketstorage.presentation.ui.screens.category.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getLocationIdFromDataStorageUseCase: GetLocationIdFromDataStorageUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase,
    private val getInventoriesByCategoryIdUseCase: GetInventoriesByCategoryIdUseCase,
    private val getInventoriesUseCase: GetInventoriesUseCase
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
                    Log.d("inventoryListCategory", "${_categoriesState.value.inventoryList}")

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
                                categories = state.existingCategoriesForCurrentLocation,
                                inventoryList = state.inventoryList
                            )
                        }

                    }
                    Log.d("inventoryListCategory", "${_categoriesState.value.inventoryList}")

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

    fun getInventoryByCategoryId(id:String){
        viewModelScope.launch {
                getInventoriesByCategoryIdUseCase.invoke(id).collect{list->
                    _categoriesState.update {
                        it.copy(
                            inventoryList = list
                        )
                    }
                }
        }
        Log.d("inventoryListCategory", "${_categoriesState.value.inventoryList}")

    }

    fun activeCategory(id:String){
        _categoriesState.update {
            it.copy(
                activeCategory = id,
            )
        }
    }

    fun clearActiveCategory(){
        _categoriesState.update {
            it.copy(
                activeCategory = "",
            )
        }
    }
    fun toggleExpandIcon(categoryId: String) {
        _categoriesState.update { state ->
            val expandedIcons = state.expandedIcons.toMutableMap()
            expandedIcons[categoryId] = !(expandedIcons[categoryId] ?: false)
            state.copy(expandedIcons = expandedIcons)
        }
    }




    
}

