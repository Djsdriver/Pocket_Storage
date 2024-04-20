package com.example.pocketstorage.presentation.ui.screens.inventory.event

import android.net.Uri

sealed interface CreateProductEvent {
    data object CreateBuilding : CreateProductEvent
    data class SetNameProduct(val nameProduct: String) : CreateProductEvent
    data class SetDescription(val description: String) : CreateProductEvent
    data class SetLocationId(val locationId: String) : CreateProductEvent
    data class SetCategoryId(val categoryId: String) : CreateProductEvent
    data class SetPathToImage(val pathToImage: Uri) : CreateProductEvent
    data object ShowListBuilding : CreateProductEvent
    data class ShowListCategory(val locationId: String) : CreateProductEvent

    //Добавить еще действий, если нужно

}