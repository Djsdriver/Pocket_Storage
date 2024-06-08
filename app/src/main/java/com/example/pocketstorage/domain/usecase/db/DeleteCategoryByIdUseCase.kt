package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class DeleteCategoryByIdUseCase(val repository: DatabaseRepository) {
    suspend operator fun invoke(categoryId: String){
        repository.deleteCategoryById(categoryId)
    }
}