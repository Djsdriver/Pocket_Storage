package com.example.pocketstorage.presentation.ui.screens.building

sealed interface CreateBuildingEvent {
    data object CreateBuilding: CreateBuildingEvent
    data class SetNameBuilding(val nameBuilding: String): CreateBuildingEvent
    data class SetIndex(val index: String): CreateBuildingEvent
    data class SetAddress(val address: String): CreateBuildingEvent
    //Добавить еще действий, если нужно

}
