package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.usecase.LogOutUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByLocationIdUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.product.GetDataFromQRCodeUseCase
import com.example.pocketstorage.presentation.ui.screens.building.BuildingUiState
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getDataFromQRCodeUseCase: GetDataFromQRCodeUseCase,
    private val getInventoriesByLocationIdUseCase: GetInventoriesByLocationIdUseCase,
    private val getLocationIdFromDataStorageUseCase: GetLocationIdFromDataStorageUseCase,

    ) : ViewModel() {


    private val _scannerState = MutableStateFlow(ScannerUiState())
    val scannerState = _scannerState.asStateFlow()

    private val _state = MutableStateFlow(ProductUIState())
    val state = _state.asStateFlow()


    fun logOut() {
        logOutUseCase.invoke()
    }

    fun startScan() {
        viewModelScope.launch {
            getDataFromQRCodeUseCase.invoke().collect { data ->
                if (!data.isNullOrBlank()) {
                    _scannerState.update {
                        it.copy(
                            data = data
                        )
                    }
                }
            }
        }
    }

    fun clearScannerState() {
        _scannerState.value = ScannerUiState()
    }


    fun event(productEvent: ProductEvent) {
        when (productEvent) {
            is ProductEvent.ShowProductSelectedBuilding -> {

                viewModelScope.launch {
                    getLocationIdFromDataStore()
                    state.collect { productUiState ->
                        if (productUiState.selectedIdBuilding.isNotEmpty()){
                            getInventoriesByLocationIdUseCase.invoke(state.value.selectedIdBuilding)
                                .collect { listProduct ->
                                    _state.update { state ->
                                        state.copy(
                                            products = listProduct.filter {
                                                it.doesMatchSearchQuery(
                                                    productUiState.searchText
                                                )
                                            }
                                        )
                                    }
                                }
                        }

                    }
                }
            }

            is ProductEvent.SetSearchText -> {
                _state.update {
                    it.copy(
                        searchText = productEvent.text
                    )
                }
            }

            is ProductEvent.StartScan -> {

            }

            is ProductEvent.CleanerScannerState -> {

            }

            is ProductEvent.LogOutProfile -> {

            }

            else -> {}
        }
    }

    fun getLocationIdFromDataStore() {
        viewModelScope.launch {
            getLocationIdFromDataStorageUseCase.invoke().collect { string ->
                if (string != null) {
                    _state.update {
                        it.copy(
                            selectedIdBuilding = string
                        )
                    }

                }
            }
        }

    }
}





