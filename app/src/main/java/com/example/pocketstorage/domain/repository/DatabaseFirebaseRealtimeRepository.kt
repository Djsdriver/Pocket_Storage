package com.example.pocketstorage.domain.repository

interface DatabaseFirebaseRealtimeRepository {

    suspend fun createUserAndLinkDatabase()
    suspend fun getDataByUid()
}
