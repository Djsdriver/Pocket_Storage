package com.example.pocketstorage.domain.usecase.firebase

import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository

class CreateUserAndLinkDatabaseUseCase(private val databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository) {

    suspend fun execute() {
        databaseFirebaseRealtimeRepository.createUserAndLinkDatabase()
    }
}
