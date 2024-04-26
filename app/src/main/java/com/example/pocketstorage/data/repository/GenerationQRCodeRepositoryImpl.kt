package com.example.pocketstorage.data.repository

import android.graphics.Bitmap
import com.example.pocketstorage.domain.repository.GenerationQrCodeRepository
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import javax.inject.Inject

class GenerationQRCodeRepositoryImpl @Inject constructor(private val barcodeEncoder: BarcodeEncoder) :
    GenerationQrCodeRepository {
    override fun generationQrCodeProduct(content: String): Bitmap? {
        try {
            val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 300, 300)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}

