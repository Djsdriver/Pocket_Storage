package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class DeleteInventoryByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(inventoryId: String) {
        databaseRepository.deleteInventoryById(inventoryId)
    }
}