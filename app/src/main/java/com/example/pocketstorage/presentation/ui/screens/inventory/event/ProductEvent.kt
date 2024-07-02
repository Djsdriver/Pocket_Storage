package com.example.pocketstorage.presentation.ui.screens.inventory.event

import com.example.pocketstorage.domain.model.Inventory

sealed interface ProductEvent {
    data object ShowProductSelectedBuilding : ProductEvent
    data object StartScan : ProductEvent
    data object CleanerScannerState : ProductEvent
    data object LogOutProfile : ProductEvent
    data object StartLoading : ProductEvent
    data object StopLoading : ProductEvent
    data class SetSearchText(val text: String) : ProductEvent
    data class PermissionCamera(val isGranted: Boolean) : ProductEvent
    data class PermissionExternalStorage(val isGranted: Boolean) : ProductEvent
    data class ImportInventoriesFromExcel(val uri: String) : ProductEvent

    data class DeleteItems(val onSuccess: ()-> Unit): ProductEvent

    class CurrentUser(val currentUser: String) : ProductEvent

    data object ExportDataInFirebase : ProductEvent

}
