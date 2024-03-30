package com.example.pocketstorage.presentation.ui.screens.inventory

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Location

sealed interface CreateProductEvent {
    data object CreateBuilding: CreateProductEvent
    data class SetNameProduct(val nameProduct: String): CreateProductEvent
    data class SetDescription(val description: String): CreateProductEvent
    data class SetLocationId(val locationId: String): CreateProductEvent
    data class SetCategoryId(val categoryId: String): CreateProductEvent
    data class SetPathToImage(val pathToImage: String): CreateProductEvent

    data class ListBuilding (val listBuilding: List<Location>) :CreateProductEvent
    data class ListCategory (val locationId: String) :CreateProductEvent
    //Добавить еще действий, если нужно

}