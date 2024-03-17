package com.example.pocketstorage.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun saveLocationIdToDataStorage(buildingId: String)

    suspend fun getLocationIdFromDataStorage(): Flow<String?>

}