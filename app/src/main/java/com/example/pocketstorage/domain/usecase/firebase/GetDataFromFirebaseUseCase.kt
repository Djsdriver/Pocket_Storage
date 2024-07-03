package com.example.pocketstorage.domain.usecase.firebase

import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository

class GetDataFromFirebaseUseCase(private val databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository) {
    suspend operator fun invoke(){
        databaseFirebaseRealtimeRepository.getDataByUid()
    }
}