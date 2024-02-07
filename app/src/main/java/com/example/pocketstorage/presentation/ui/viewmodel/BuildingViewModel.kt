package com.example.pocketstorage.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update



@OptIn(FlowPreview::class)
class BuildingViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _building = MutableStateFlow(allBuilding)

    val filteredPersons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .map { text ->
            if (text.isBlank()) {
                allBuilding
            } else {
                delay(2000L)
                allBuilding.filter { it.doesMatchSearchQuery(text) }
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
}

data class BuildingModel2(
    val nameSchool: String, val shortCode: String, val address: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            nameSchool,
            shortCode,
            address
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allBuilding = listOf(
    BuildingModel2("ГБОУ Школа №1500", "MSK-1", "Skornyazhnyy Pereulok, 3, Moscow"),
BuildingModel2("Школа № 1284", "MSK-2", "Ulanskiy Pereulok, 8, Moscow"),
BuildingModel2("Школа №57", "MSK-1", "Malyy Znamenskiy Ln, 7/10 стр. 5, Moscow"),
BuildingModel2("ГБОУ Школа №1500", "MSK-5", "Skornyazhnyy Pereulok, 3, Moscow"),
BuildingModel2("Школа № 1284", "MSK-2", "Ulanskiy Pereulok, 8, Moscow"),
BuildingModel2("Школа №57", "MSK-1", "Malyy Znamenskiy Ln, 7/10 стр. 5, Moscow"),
BuildingModel2("ГБОУ Школа №1500", "MSK-9", "Skornyazhnyy Pereulok, 3, Moscow"),
BuildingModel2("Школа № 1284", "MSK-2", "Ulanskiy Pereulok, 8, Moscow"),
BuildingModel2("Школа №57", "MSK-1", "Malyy Znamenskiy Ln, 7/10 стр. 5, Moscow"),
BuildingModel2("ГБОУ Школа №1500", "MSK-1", "Skornyazhnyy Pereulok, 3, Moscow"),
BuildingModel2("Школа № 1284", "MSK-2", "Ulanskiy Pereulok, 8, Moscow"),
BuildingModel2("Школа №57", "MSK-1", "Malyy Znamenskiy Ln, 7/10 стр. 5, Moscow"))


//Можно использовать такой подход через combine
/*@OptIn(FlowPreview::class)
class BuildingViewModel:ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) { text, persons ->
            if(text.isBlank()) {
                persons
            } else {
                delay(2000L)
                persons.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class BuildingModel2(
    val nameSchool: String, val shortCode: String, val address: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            nameSchool,
            "${nameSchool.first()} ${shortCode.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}*/
