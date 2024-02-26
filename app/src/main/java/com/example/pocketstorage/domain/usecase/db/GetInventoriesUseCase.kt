package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetInventoriesUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<Inventory>> {
        return databaseRepository.getInventories()
    }
}