package com.example.pocketstorage.domain.repository

import android.graphics.Bitmap

interface GenerationQrCodeRepository {

    fun generationQrCodeProduct(content: String): Bitmap?

}