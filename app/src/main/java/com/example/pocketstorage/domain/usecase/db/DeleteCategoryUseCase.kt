package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.repository.DatabaseRepository

class DeleteCategoryUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(category: Category) {
        databaseRepository.deleteCategory(category)
    }
}