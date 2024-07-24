package com.example.pocketstorage.domain.usecase.sharedQrCode

import android.graphics.Bitmap
import com.example.pocketstorage.domain.repository.SharedRepository

class SharedQrCodeUseCase(private val sharedRepository: SharedRepository) {
    operator fun invoke(bitmap: Bitmap){
        sharedRepository.shareQrCode(bitmap)
    }

}