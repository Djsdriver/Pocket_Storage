package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.usecase.db.GetCategoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationByIdUseCase
import com.example.pocketstorage.domain.usecase.product.GenerationQrCodeProductUseCase
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductPageEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductPageViewModel @Inject constructor(
    private val getInventoryByIdUseCase: GetInventoryByIdUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val generationQrCodeProductUseCase: GenerationQrCodeProductUseCase
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
        }
    }

    private fun showInfoProductById(id: String) {
        viewModelScope.launch {
            val product = getInventoryByIdUseCase.invoke(id)
            val nameCategory = getCategoryByIdUseCase.invoke(product.categoryId)
            val nameBuilding = getLocationByIdUseCase.invoke(product.locationId)
            _state.update {
                it.copy(
                    name = product.name,
                    idLocation = product.locationId,
                    idCategory = product.categoryId,
                    description = product.description,
                    nameCategory = nameCategory.name,
                    nameBuilding = nameBuilding.name,
                    pathToImage = product.pathToImage ?: ""
                )
            }
            Log.d("_stateP", "${_state.value.nameBuilding}")
        }
    }

    fun generateQRCode(content: String): Bitmap? {
        return generationQrCodeProductUseCase.invoke(content)
    }


}