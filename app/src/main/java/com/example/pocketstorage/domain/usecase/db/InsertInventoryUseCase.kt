package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository

class InsertInventoryUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(inventory: Inventory) {
        databaseRepository.insertInventory(inventory)
    }
}