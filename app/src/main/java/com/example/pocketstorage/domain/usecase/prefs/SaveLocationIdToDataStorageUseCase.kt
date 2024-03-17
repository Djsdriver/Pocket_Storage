package com.example.pocketstorage.domain.usecase.prefs

import com.example.pocketstorage.domain.repository.PreferencesRepository

class SaveLocationIdToDataStorageUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(buildingId: String) {
        preferencesRepository.saveLocationIdToDataStorage(buildingId)
    }
}