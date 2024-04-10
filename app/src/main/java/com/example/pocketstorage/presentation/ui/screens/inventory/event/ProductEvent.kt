package com.example.pocketstorage.presentation.ui.screens.inventory.event

sealed interface ProductEvent{
    data object ShowProductSelectedBuilding : ProductEvent
    data object StartScan : ProductEvent
    data object CleanerScannerState : ProductEvent
    data object LogOutProfile : ProductEvent
    data class SetSearchText(val text : String) : ProductEvent

}