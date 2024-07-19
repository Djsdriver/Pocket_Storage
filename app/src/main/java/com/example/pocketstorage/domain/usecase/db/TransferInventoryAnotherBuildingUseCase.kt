package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class TransferInventoryAnotherBuildingUseCase(private val databaseRepository: DatabaseRepository) {

    suspend operator fun invoke(
        inventoryId: String,
        newLocationId: String,
        newCategoryId: String
    ){
        databaseRepository.transferInventoryAnotherBuilding(inventoryId, newLocationId, newCategoryId)
    }


}