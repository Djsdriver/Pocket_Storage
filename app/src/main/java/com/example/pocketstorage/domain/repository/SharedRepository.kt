package com.example.pocketstorage.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface SharedRepository {

    fun shareQrCode(bitmap: Bitmap)

}