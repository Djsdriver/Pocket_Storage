package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.repository.DatabaseRepository

class GetCategoryByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(categoryId: String): Category {
        return databaseRepository.getCategoryById(categoryId)
    }
}