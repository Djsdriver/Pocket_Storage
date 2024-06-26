package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository

class InsertInventoryFromExcelUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(inventory: Inventory) {
        databaseRepository.insertInventoryFromExcel(inventory)
    }
}
