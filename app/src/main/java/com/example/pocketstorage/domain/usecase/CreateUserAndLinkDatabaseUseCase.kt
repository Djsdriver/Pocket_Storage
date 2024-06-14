package com.example.pocketstorage.domain.usecase

import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository


class CreateUserAndLinkDatabaseUseCase(private val databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository) {

    suspend fun execute() {
        databaseFirebaseRealtimeRepository.createUserAndLinkDatabase()
    }
}
