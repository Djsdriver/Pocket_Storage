package com.example.pocketstorage.domain.usecase.product

import android.graphics.Bitmap
import com.example.pocketstorage.domain.repository.GenerationQrCodeRepository

class GenerationQrCodeProductUseCase(private val generationQrCodeRepository: GenerationQrCodeRepository) {


    operator fun invoke(content: String): Bitmap?{
        return generationQrCodeRepository.generationQrCodeProduct(content)
    }
}