package com.example.pocketstorage.data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.pocketstorage.domain.repository.SharedRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class SharedRepositoryImpl @Inject constructor(val context : Context): SharedRepository {
    override fun shareQrCode(bitmap: Bitmap) {
        val context = context
        // Сохранение QR-кода в файл
        val file = File(context.cacheDir, "qr_code.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        // Создание Intent для обмена
        val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share QR Code").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

}
