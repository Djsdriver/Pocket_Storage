package com.example.pocketstorage.domain.repository

import com.example.pocketstorage.domain.model.UserDatabaseRealtime

interface DatabaseFirebaseRealtimeRepository {

    suspend fun createUserAndLinkDatabase()
}