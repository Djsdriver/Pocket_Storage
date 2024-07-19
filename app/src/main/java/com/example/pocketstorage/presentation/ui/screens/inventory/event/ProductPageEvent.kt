package com.example.pocketstorage.presentation.ui.screens.inventory.event

sealed interface ProductPageEvent{
    data class ShowInfoProduct(val idProduct: String) : ProductPageEvent
    data class GenerationQrCode(val content: String) : ProductPageEvent
    data object ShowListLocation : ProductPageEvent
    class UpdateNameProduct(val inventoryId: String, val newName: String): ProductPageEvent
    data object ShowListCategory : ProductPageEvent
    class SelectedBuildingIdForTransfer(val locationId: String) : ProductPageEvent
    class SelectedCategoryIdForTransfer(val categoryId: String) : ProductPageEvent
    data object SaveTransferToAnotherBuilding : ProductPageEvent
}