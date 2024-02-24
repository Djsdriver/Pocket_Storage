package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<Category>> {
        return databaseRepository.getCategories()
    }
}