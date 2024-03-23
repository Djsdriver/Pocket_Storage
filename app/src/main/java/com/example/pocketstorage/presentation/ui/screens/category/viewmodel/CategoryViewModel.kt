package com.example.pocketstorage.presentation.ui.screens.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.doesMatchSearchQuery
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getLocationIdFromDataStorageUseCase: GetLocationIdFromDataStorageUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase
) : ViewModel() {

    private val _currentLocationId: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentLocationId: StateFlow<String?> = _currentLocationId.asStateFlow()

    private val _existingCategoriesForCurrentLocation: MutableStateFlow<List<Category>> =
        MutableStateFlow(emptyList())
    val existingCategoriesForCurrentLocation: StateFlow<List<Category>> =
        _existingCategoriesForCurrentLocation.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(existingCategoriesForCurrentLocation.value)

    init {
        viewModelScope.launch {

            getCurrentLocationId()

            currentLocationId.collect { currentLocationId ->
                if (currentLocationId != null) {
                    getAllCategoriesByLocationId(currentLocationId)
                }
            }

        }
    }

    val filteredCategories = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .map { text ->
            if (text.isBlank()) {
                existingCategoriesForCurrentLocation.value
            } else {
                val categories = existingCategoriesForCurrentLocation.value.filter {
                    it.doesMatchSearchQuery(text)
                }

                Log.d("CAT", categories.toString())

                categories
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = _categories.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
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
                    _currentLocationId.value = currentLocationId
                }
            }
        }
    }

    fun getAllCategoriesByLocationId(locationId: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            getCategoriesByBuildingIdUseCase.invoke(locationId).collect {
                _existingCategoriesForCurrentLocation.value = it
            }
        }
    }
}

