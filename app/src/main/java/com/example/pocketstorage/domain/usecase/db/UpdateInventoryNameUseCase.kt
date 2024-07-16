package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class UpdateInventoryNameUseCase(private val repository: DatabaseRepository) {

    suspend operator fun invoke(inventoryId: String, newName: String){
        repository.updateInventoryName(inventoryId, newName)
    }


}