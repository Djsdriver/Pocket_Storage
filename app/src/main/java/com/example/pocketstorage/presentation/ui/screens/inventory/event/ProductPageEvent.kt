package com.example.pocketstorage.presentation.ui.screens.inventory.event

import android.content.Context
import android.graphics.Bitmap
import java.io.File

sealed class ProductPageEvent{
    data class ShowInfoProduct(val idProduct: String) : ProductPageEvent()
    data class GenerationQrCode(val content: String) : ProductPageEvent()
    data object ShowListLocation : ProductPageEvent()
    class UpdateNameProduct(val inventoryId: String, val newName: String): ProductPageEvent()
    data object ShowListCategory : ProductPageEvent()
    class SelectedBuildingIdForTransfer(val locationId: String) : ProductPageEvent()
    class SelectedCategoryIdForTransfer(val categoryId: String) : ProductPageEvent()
    data object SaveTransferToAnotherBuilding : ProductPageEvent()
    class SharedQrCode(val bitmap: Bitmap, val outputDir: File, val context: Context) : ProductPageEvent()
}