package com.example.pocketstorage.domain.usecase.sharedQrCode

import android.graphics.Bitmap
import com.example.pocketstorage.domain.repository.SharedRepository
import java.io.File
import javax.inject.Inject

class SaveQrCodeUseCase @Inject constructor(private val sharedRepository: SharedRepository) {
    operator fun invoke(bitmap: Bitmap, outputDir: File): File{
        return sharedRepository.saveQrCode(bitmap, outputDir)
    }
}