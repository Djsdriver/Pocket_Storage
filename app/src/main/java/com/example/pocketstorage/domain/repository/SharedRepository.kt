package com.example.pocketstorage.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface SharedRepository {
    fun saveQrCode(bitmap: Bitmap, outputDir: File): File

    fun shareQrCode(file: File, context: Context)

}