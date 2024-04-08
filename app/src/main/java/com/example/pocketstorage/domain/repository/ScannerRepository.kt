package com.example.pocketstorage.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScannerRepository {

    fun scanProduct(): Flow<String>
}