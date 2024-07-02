package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.repository.DatabaseRepository

class InsertLocationFromExcelUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(location: Location) {
        databaseRepository.insertLocationFromExcel(location)
    }
}