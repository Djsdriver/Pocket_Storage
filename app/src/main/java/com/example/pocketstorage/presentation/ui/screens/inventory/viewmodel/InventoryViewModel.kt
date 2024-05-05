package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.TableInventory
import com.example.pocketstorage.domain.usecase.LogOutUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoryNameByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByLocationIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationNameByIdUseCase
import com.example.pocketstorage.domain.usecase.excel.ExportInventoriesToExcelFileUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.product.GetDataFromQRCodeUseCase
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductUIState
import com.example.pocketstorage.utils.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val getInventoriesUseCase: GetInventoriesUseCase,
    private val exportInventoriesToExcelFileUseCase: ExportInventoriesToExcelFileUseCase,
    private val getCategoryNameByIdUseCase: GetCategoryNameByIdUseCase,
    private val getLocationByIdUseCase: GetLocationByIdUseCase
) : ViewModel() {

    private val _scannerState = MutableStateFlow(ScannerUiState())
    val scannerState = _scannerState.asStateFlow()

    private val _state = MutableStateFlow(ProductUIState())
    val state = _state.asStateFlow()

    fun event(productEvent: ProductEvent) {
        when (productEvent) {
            is ProductEvent.ShowProductSelectedBuilding -> {

                viewModelScope.launch {
                    getLocationIdFromDataStore()
                    state.collect { productUiState ->
                        if (productUiState.selectedIdBuilding.isNotEmpty()) {
                            getInventoriesByLocationIdUseCase.invoke(state.value.selectedIdBuilding)
                                .collect { listProduct ->
                                    _state.update { state ->
                                        state.copy(products = listProduct.filter {
                                            it.doesMatchSearchQuery(
                                                productUiState.searchText
                                            )
                                        })
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
                startScan()
            }

            is ProductEvent.PermissionCamera -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            permissionCamera = productEvent.isGranted
                        )
                    }
                }
            }

            is ProductEvent.PermissionExternalStorage -> {
                if (productEvent.isGranted) {

                    val tableInventoryList: ArrayList<TableInventory> = ArrayList()

                    val inventories = state.value.products

                    var count = 0

                    viewModelScope.launch {
                        inventories.forEach { inventory ->
                            val categoryName = getCategoryNameByIdUseCase(inventory.categoryId)
                            val location = getLocationByIdUseCase(inventory.locationId)
                            val tableInventory = TableInventory(
                                id = (++count).toString(),
                                name = inventory.name,
                                description = inventory.description,
                                categoryName = categoryName,
                                locationName = location.name,
                                locationIndex = location.index,
                                locationAddress = location.address
                            )

                            tableInventoryList.add(tableInventory)
                        }

                        exportInventoriesToExcelFileUseCase(tableInventoryList)

                        _state.update {
                           it.copy(
                               toastNotification = true
                           )
                        }
                    }
                } else {
                    Log.d("STORAGE", "IS NOT GRANTED IN VIEWMODEL")
                }
            }

            is ProductEvent.CleanerScannerState -> {
                clearScannerState()
            }

            is ProductEvent.LogOutProfile -> {
                logOut()
            }

            is ProductEvent.StartLoading -> {
                _state.update {
                    it.copy(loading = true)
                }
            }

            is ProductEvent.StopLoading -> {
                _state.update {
                    it.copy(loading = false)
                }
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

    private fun startScan() {
        viewModelScope.launch {
            getDataFromQRCodeUseCase.invoke().collect { data ->
                getInventoriesUseCase.invoke().collect { productIds ->
                    if (productIds.any { it.id == data }) {
                        _scannerState.update {
                            it.copy(data = data)
                        }
                    } else {
                        SnackbarManager.showMessage(R.string.nothing_found)
                    }
                }
            }
        }
    }

    private fun clearScannerState() {
        _scannerState.value = ScannerUiState()
    }

    private fun logOut() {
        logOutUseCase.invoke()
    }

    fun resetToastNotificationState() {
        _state.update {
            it.copy(
                toastNotification = false
            )
        }
    }
}





