package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class GetCategoryNameByIdUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(categoryId: String): String {
        return databaseRepository.getCategoryNameById(categoryId)
    }
}