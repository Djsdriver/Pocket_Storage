package com.example.pocketstorage.presentation.ui.screens.inventory.event

import android.graphics.Bitmap

sealed interface ProductPageEvent{
    data class ShowInfoProduct(val idProduct: String) : ProductPageEvent
    data class GenerationQrCode(val content: String) : ProductPageEvent
}