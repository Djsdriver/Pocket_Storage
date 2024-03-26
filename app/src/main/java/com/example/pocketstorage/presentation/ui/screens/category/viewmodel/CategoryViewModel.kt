package com.example.pocketstorage.presentation.ui.screens.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.model.doesMatchSearchQuery
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.presentation.ui.screens.building.BuildingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesStateForCurrentLocation(
    val currentLocationId: String = "",
    val existingCategoriesForCurrentLocation: List<Category> = emptyList(),
    val searchText: String = "",

)

sealed class CategoriesUiState{
    data object Loading : CategoriesUiState()
    data class Success(
        val categories: List<Category> = emptyList(),
    ) : CategoriesUiState() {
        fun isEmpty() = categories.isEmpty()
    }
}
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getLocationIdFromDataStorageUseCase: GetLocationIdFromDataStorageUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase
) : ViewModel() {

    private val _categoriesState = MutableStateFlow(CategoriesStateForCurrentLocation())
    val categoriesState: StateFlow<CategoriesStateForCurrentLocation?> = _categoriesState.asStateFlow()

    private val _uiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState.Loading)

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

    fun saveCategoryOnLocalStorage(category: Category): CompletableDeferred<Unit> {
        val completableDeferred = CompletableDeferred<Unit>()
        viewModelScope.launch {
            try {
                insertCategoryUseCase(category)
                completableDeferred.complete(Unit)
            } catch (e: Exception) {
                completableDeferred.completeExceptionally(e)
            }
        }
        return completableDeferred
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

