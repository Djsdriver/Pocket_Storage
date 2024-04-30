package com.example.pocketstorage.domain.usecase.db

import android.graphics.Bitmap
import android.net.Uri
import com.example.pocketstorage.domain.repository.DatabaseRepository

class SaveImageToPrivateStorageBitmapUseCase(private val databaseRepository: DatabaseRepository) {

    suspend operator fun invoke(bitmap: Bitmap,nameOfImage: String) : Uri {
       return databaseRepository.saveImageToPrivateStorageBitmap(bitmap,nameOfImage)
    }
}