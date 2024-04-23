package com.example.pocketstorage.presentation.ui.screens.inventory.event

sealed interface ProductPageEvent{
    data class ShowInfoProduct(val idProduct: String) : ProductPageEvent
}