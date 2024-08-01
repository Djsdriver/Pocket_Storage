package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.usecase.db.GetCategoriesByBuildingIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.domain.usecase.db.TransferInventoryAnotherBuildingUseCase
import com.example.pocketstorage.domain.usecase.db.UpdateInventoryNameUseCase
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.product.GenerationQrCodeProductUseCase
import com.example.pocketstorage.domain.usecase.sharedQrCode.SaveQrCodeUseCase
import com.example.pocketstorage.domain.usecase.sharedQrCode.ShareQrCodeUseCase
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductPageEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductPageUiState
import com.example.pocketstorage.utils.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductPageViewModel @Inject constructor(
    private val getInventoryByIdUseCase: GetInventoryByIdUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val generationQrCodeProductUseCase: GenerationQrCodeProductUseCase,
    private val updateInventoryNameUseCase: UpdateInventoryNameUseCase,
    private val getCategoriesByBuildingIdUseCase: GetCategoriesByBuildingIdUseCase,
    private val transferInventoryAnotherBuildingUseCase: TransferInventoryAnotherBuildingUseCase,
    private val shareQrCodeUseCase: ShareQrCodeUseCase,
    private val saveQrCodeUseCase: SaveQrCodeUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(ProductPageUiState())
    val state = _state.asStateFlow()


    fun event(productPageEvent: ProductPageEvent) {
        when (productPageEvent) {
            is ProductPageEvent.ShowInfoProduct -> {
                _state.update {
                    it.copy(idProduct = productPageEvent.idProduct)
                }
                showInfoProductById(_state.value.idProduct)
            }

            is ProductPageEvent.GenerationQrCode -> {
                _state.update {
                    it.copy(generatedBitmap = generateQRCode(productPageEvent.content))
                }
            }

            is ProductPageEvent.ShowListLocation -> {
                showListLocation()
            }

            is ProductPageEvent.UpdateNameProduct -> {
                updateNameProduct(productPageEvent.inventoryId, productPageEvent.newName)
            }

            is ProductPageEvent.ShowListCategory -> {
                showListCategory()
            }

            is ProductPageEvent.SelectedBuildingIdForTransfer -> {
                _state.update {
                    it.copy(
                        selectedBuildingIdForTransfer = productPageEvent.locationId
                    )
                }
            }

            is ProductPageEvent.SelectedCategoryIdForTransfer -> {
                _state.update {
                    it.copy(
                        selectedCategoryIdForTransfer = productPageEvent.categoryId
                    )
                }
            }

            ProductPageEvent.SaveTransferToAnotherBuilding -> {
                transferToAnotherBuilding()
            }

            is ProductPageEvent.SharedQrCode -> {
                saveAndSharedQrCode(productPageEvent.bitmap, productPageEvent.outputDir, productPageEvent.context)
            }
        }
    }

    private fun saveAndSharedQrCode(bitmap: Bitmap,outputDir: File, context: Context){
        val file = saveQrCodeUseCase.invoke(bitmap,outputDir)
        shareQrCodeUseCase.invoke(file,context)
    }

    private fun transferToAnotherBuilding(){
        val currentInventoryId = state.value.idProduct
        val buildingId = state.value.selectedBuildingIdForTransfer
        val categoryId = state.value.selectedCategoryIdForTransfer
        viewModelScope.launch {
            transferInventoryAnotherBuildingUseCase.invoke(currentInventoryId,buildingId,categoryId)
            showInfoProductById(_state.value.idProduct)
        }
    }

    private fun showListCategory(){
        viewModelScope.launch {
            val buildingId = _state.value.selectedBuildingIdForTransfer
            getCategoriesByBuildingIdUseCase.invoke(buildingId)
                .collect { category ->
                    _state.update {
                        it.copy(
                            listCategory = category
                        )
                    }
                }
        }
    }

    private fun updateNameProduct(inventoryId: String, newName: String){
        viewModelScope.launch {
            if (newName.isNotEmpty()){
                updateInventoryNameUseCase.invoke(inventoryId, newName)
                showInfoProductById(_state.value.idProduct)
            } else {
                SnackbarManager.showMessage("Поле пустое")
            }

        }
    }

    private fun showInfoProductById(id: String) {
        viewModelScope.launch {
            val product = getInventoryByIdUseCase.invoke(id)
            val nameCategory = product?.let { getCategoryByIdUseCase.invoke(it.categoryId) }
            val nameBuilding = product?.let { getLocationByIdUseCase.invoke(it.locationId) }
            if (product != null) {
                _state.update {
                    it.copy(
                        name = product.name,
                        idLocation = product.locationId,
                        idCategory = product.categoryId,
                        description = product.description,
                        nameCategory = nameCategory!!.name,
                        nameBuilding = nameBuilding!!.name,
                        address = nameBuilding.address,
                        pathToImage = product.pathToImage ?: ""
                    )
                }
            }
        }
    }

     private fun generateQRCode(content: String): Bitmap? {
        return generationQrCodeProductUseCase.invoke(content)
    }

    private fun showListLocation(){
        viewModelScope.launch {
            getLocationsUseCase.invoke().collect{ list ->
                _state.update {
                    it.copy(
                        listLocation = list
                    )
                }
            }
        }
    }
}