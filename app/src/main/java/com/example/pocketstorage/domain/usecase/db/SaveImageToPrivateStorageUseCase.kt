package com.example.pocketstorage.domain.usecase.db

import android.net.Uri
import com.example.pocketstorage.domain.repository.DatabaseRepository

class SaveImageToPrivateStorageUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(uri: Uri, nameOfImage: String): String {
        return databaseRepository.saveImageToPrivateStorage(uri, nameOfImage)
    }
}