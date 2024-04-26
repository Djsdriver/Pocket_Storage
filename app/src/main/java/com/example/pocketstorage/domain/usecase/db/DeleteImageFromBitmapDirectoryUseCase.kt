package com.example.pocketstorage.domain.usecase.db

import android.graphics.Bitmap
import android.net.Uri
import com.example.pocketstorage.domain.repository.DatabaseRepository

class DeleteImageFromBitmapDirectoryUseCase(private val databaseRepository: DatabaseRepository) {

    suspend operator fun invoke(imagePath: String?){
        return databaseRepository.deleteImageFromStorage(imagePath)
    }

}