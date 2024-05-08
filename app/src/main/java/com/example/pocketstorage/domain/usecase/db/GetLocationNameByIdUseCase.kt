package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class GetLocationNameByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(locationId: String): String {
        return databaseRepository.getLocationNameById(locationId)
    }
}