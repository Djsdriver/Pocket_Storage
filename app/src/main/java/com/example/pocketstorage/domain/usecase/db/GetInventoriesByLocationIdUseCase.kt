package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetInventoriesByLocationIdUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(locationId: Long): Flow<List<Inventory>> {
        return databaseRepository.getInventoriesByLocationId(locationId)
    }
}