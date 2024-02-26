package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetLocationsUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<Location>> {
        return databaseRepository.getLocations()
    }
}