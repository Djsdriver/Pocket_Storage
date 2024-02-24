package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository

class GetInventoryByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(inventoryId: Long): Inventory {
        return databaseRepository.getInventoryById(inventoryId)
    }
}