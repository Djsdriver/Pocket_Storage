package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetInventoriesByCategoryIdUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(categoryId: String): Flow<List<Inventory?>> {
        return databaseRepository.getInventoriesByCategoryId(categoryId)
    }
}