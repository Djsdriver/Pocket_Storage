package com.example.pocketstorage.presentation.ui.screens.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.usecase.db.GetInventoryByIdUseCase
import com.example.pocketstorage.presentation.ui.screens.inventory.event.ProductPageEvent
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductPageUiState
import com.example.pocketstorage.presentation.ui.screens.inventory.stateui.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductPageViewModel @Inject constructor(
    private val getInventoryByIdUseCase: GetInventoryByIdUseCase
): ViewModel() {


    private val _state = MutableStateFlow(ProductPageUiState())
    val state = _state.asStateFlow()


    fun event(productPageEvent: ProductPageEvent){
        when (productPageEvent){
            is ProductPageEvent.ShowInfoProduct ->{
                _state.update {
                    it.copy(idProduct = productPageEvent.idProduct)
                }
                showInfoProductById(_state.value.idProduct)
            }
        }
    }

    private fun showInfoProductById(id: String) {
        viewModelScope.launch {
            val product = getInventoryByIdUseCase.invoke(id)
            _state.update {
                it.copy(
                    name = product.name,
                    location = product.locationId,
                    category = product.categoryId,
                    description = product.description
                )
            }
        }
    }


}