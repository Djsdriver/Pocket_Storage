package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.repository.DatabaseRepository

class GetLocationByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(locationId: Long): Location {
        return databaseRepository.getLocationById(locationId)
    }
}