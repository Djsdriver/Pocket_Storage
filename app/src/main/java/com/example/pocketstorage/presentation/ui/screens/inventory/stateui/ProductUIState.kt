package com.example.pocketstorage.presentation.ui.screens.inventory.stateui

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.utils.Notification

data class ProductUIState(
    val name: String = "",
    val description: String = " ",
    val locationId: String = "",
    val categoryId: String = "",
    val pathToImage: String? = "",
    val products: List<Inventory?> = emptyList(),
    val selectedIdBuilding: String = "",
    val data:String = "",
    val searchText : String = "",
    val loading: Boolean = false,
    val permissionCamera: Boolean = false,
    val toastNotification: Notification = Notification(),
    val isSelected : Boolean = false,
    val showCheckbox: Boolean = false,
    val isSelectedList : MutableList<String> = mutableListOf()
)
