package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.model.TableInventory
import com.example.pocketstorage.domain.usecase.LogOutUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoriesUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoryNameByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByLocationIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.db.InsertInventoryUseCase
import com.example.pocketstorage.domain.usecase.db.InsertLocationUseCase
import com.example.pocketstorage.domain.usecase.excel.ExportInventoriesToExcelFileUseCase
import com.example.pocketstorage.domain.usecase.excel.ImportInventoriesFromExcelFileUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.product.GetDataFromQRCodeUseCase
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductUIState
import com.example.pocketstorage.utils.Notification
import com.example.pocketstorage.utils.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val importInventoriesFromExcelFileUseCase: ImportInventoriesFromExcelFileUseCase,
    private val insertInventoryUseCase: InsertInventoryUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val insertLocationUseCase: InsertLocationUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _scannerState = MutableStateFlow(ScannerUiState())
    val scannerState = _scannerState.asStateFlow()

    private val _state = MutableStateFlow(ProductUIState())
    val state = _state.asStateFlow()


    fun showCheckbox(check: Boolean){
        _state.update {
            it.copy(
                showCheckbox = check
            )
        }
    }

    fun update(check: Boolean){
        _state.update {
            it.copy(
                isSelected = check
            )
        }
    }

    fun addListSelected(inventoryId: String, isChecked: Boolean) {
        _state.update { currentState ->
            val updatedList = currentState.isSelectedList.toMutableList()

            if (isChecked) {
                updatedList.add(inventoryId)
            } else {
                updatedList.remove(inventoryId)
            }

            currentState.copy(
                isSelectedList = updatedList.toMutableList()
            )
        }
        Log.d("listId"," ${_state.value.isSelectedList}")

    }



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

                    if (state.value.products.isNotEmpty()) {
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
                                    locationAddress = location.address,
                                )

                                tableInventoryList.add(tableInventory)
                            }

                            exportInventoriesToExcelFileUseCase(tableInventoryList)

                            _state.update {
                                it.copy(
                                    toastNotification = Notification(
                                        isActivated = true,
                                        message = "The products are exported to an excel file" +
                                                " in the Downloads folder"
                                    )
                                )
                            }
                        }
                    } else {
                        _state.update {
                            it.copy(
                                toastNotification = Notification(
                                    isActivated = true,
                                    message = "Inventory list is empty for current building"
                                )
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            toastNotification = Notification(
                                isActivated = true,
                                message = "Permission has not been granted. " +
                                        "You can get it in app settings"
                            )
                        )
                    }
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

            is ProductEvent.ImportInventoriesFromExcel -> {
                importFile(productEvent.uri)
            }

            else -> {}
        }
    }

    private fun importFile(uri: String) {
        viewModelScope.launch {
            val list = importInventoriesFromExcelFileUseCase.invoke(uriFile = uri)

            val locationName = list[0].locationName
            val locationIndex = list[0].locationIndex
            val locationAddress = list[0].locationAddress
            Log.d("buildingExists0", "$locationName $locationAddress")

            val buildingExists = doesBuildingExist(locationAddress)
            Log.d("buildingExists1", "$buildingExists")

            if (buildingExists) {
                SnackbarManager.showMessage("Здание уже существует в базе данных")
            } else {
                insertBuildingAndInventoriesAndCategories(locationName, locationIndex, locationAddress, list)
            }
        }
    }
    //Пробегаюсь по списку списку зданий и проверяю, сушествует ли адрес данного здания
    private suspend fun doesBuildingExist(locationAddress: String): Boolean {
        val buildingExistsState = MutableStateFlow(false)

        getLocationsUseCase.invoke().collect { locations ->
            buildingExistsState.value = locations.any { it.address == locationAddress }
        }
        return buildingExistsState.value
    }

    //Вставка данных в базу данных
    private fun insertBuildingAndInventoriesAndCategories(
        locationName: String, locationIndex: String, locationAddress: String, list: List<TableInventory>
    ) {
        var buildingId = ""
        viewModelScope.launch {
            insertLocationUseCase.invoke(
                Location(name = locationName, index = locationIndex, address = locationAddress)
            )

            getLocationsUseCase.invoke().collect { locations ->
                locations.forEach { location ->
                    if (location.address == locationAddress) {
                        buildingId = location.id
                    }
                }

                val categoriesMap = mutableMapOf<String, String>()

                list.forEach {
                    val categoryName = it.categoryName
                    if (!categoriesMap.containsKey(categoryName)) {
                        insertCategoryUseCase.invoke(Category(name = categoryName, buildingId = buildingId))
                        getCategoriesUseCase.invoke().collect { categories ->
                            categories.forEach { category ->
                                if (category.name == categoryName) {
                                    categoriesMap[categoryName] = category.id
                                }
                            }
                        }
                    }

                    val categoryId = categoriesMap[categoryName] ?: ""
                    insertInventoryUseCase.invoke(Inventory(name = it.name, description = it.description, locationId = buildingId, categoryId = categoryId))
                }

                SnackbarManager.showMessage("Здание успешно импортировано.")
            }
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
                toastNotification = Notification(isActivated = false)
            )
        }
    }
}
