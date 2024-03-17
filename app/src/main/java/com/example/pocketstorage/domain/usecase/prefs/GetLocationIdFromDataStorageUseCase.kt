package com.example.pocketstorage.domain.usecase.prefs

import com.example.pocketstorage.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetLocationIdFromDataStorageUseCase(private val preferencesRepository: PreferencesRepository) {

    suspend operator fun invoke(): Flow<String?> {
        return preferencesRepository.getLocationIdFromDataStorage()
    }
}