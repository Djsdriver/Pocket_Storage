package com.example.pocketstorage.domain.usecase.db

import com.example.pocketstorage.domain.repository.DatabaseRepository

class DeleteLocationByIdUseCase(val repository: DatabaseRepository) {

    suspend operator fun invoke(buildingId: String){
        repository.deleteLocationAndRelatedEntities(buildingId)
    }
}