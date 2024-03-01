package com.example.pocketstorage.domain.usecase.databaseRealtime

import com.example.pocketstorage.domain.model.UserDatabaseRealtime
import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository

class CreateUserAndLinkDatabaseUseCase(private val databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository) {

    suspend fun execute() {
        databaseFirebaseRealtimeRepository.createUserAndLinkDatabase()
    }
}