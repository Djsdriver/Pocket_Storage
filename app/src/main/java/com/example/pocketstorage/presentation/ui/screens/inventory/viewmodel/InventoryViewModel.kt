package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.usecase.LogOutUseCase
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
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _building = MutableStateFlow(allInventory)

    val filteredInventory = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .map { text ->
            if (text.isBlank()) {
                allInventory
            } else {
                delay(2000L)
                allInventory.filter { it.doesMatchSearchQuery(text) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _building.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun logOut(){
       logOutUseCase.invoke()
    }

}

data class InventoryModel(val name: String, val image: Int) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }


}

private val allInventory = listOf(
    InventoryModel("HP LaserJet Pro 4003dw", R.drawable.ic_launcher_foreground),
    InventoryModel("Logitech M110", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR55", R.drawable.ic_launcher_foreground),
    InventoryModel("HP LaserJet Pro 4003dw", R.drawable.ic_launcher_foreground),
    InventoryModel("Logitech M110", R.drawable.ic_launcher_foreground),
    InventoryModel("Logitech F", R.drawable.ic_launcher_foreground),
    InventoryModel("Logitech G110", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR55", R.drawable.ic_launcher_foreground),
    InventoryModel("HP LaserJet Pro 4003dw", R.drawable.ic_launcher_foreground),
    InventoryModel("Logitech M110", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR55", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR60", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR90", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR55", R.drawable.ic_launcher_foreground),
    InventoryModel("Samsung UR55", R.drawable.ic_launcher_foreground),
)




