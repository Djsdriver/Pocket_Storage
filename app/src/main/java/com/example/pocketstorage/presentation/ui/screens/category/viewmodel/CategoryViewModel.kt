package com.example.pocketstorage.presentation.ui.screens.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CategoryViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categories = MutableStateFlow(allCategories)

    val filteredCategories = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .map { text ->
            if (text.isBlank()) {
                allCategories
            } else {
                delay(2000L)
                allCategories.filter { it.doesMatchSearchQuery(text) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _categories.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class CategoryModel2(
    val name: String,
    val image: Int,
    val countProduct: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

val allCategories = listOf(
    CategoryModel2(name = "Printers", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "Monitors", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "Scanners", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "Cameras", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "Keyboards", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "Headphones", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "GamePads", R.drawable.ic_launcher_foreground, "Product: 4"),
    CategoryModel2(name = "SoundMonitors", R.drawable.ic_launcher_foreground, "Product: 4"),
)