package com.example.pocketstorage.domain.usecase.sharedQrCode

import android.content.Context
import com.example.pocketstorage.domain.repository.SharedRepository
import java.io.File
import javax.inject.Inject

class ShareQrCodeUseCase @Inject constructor(private val sharedRepository: SharedRepository) {

    operator fun invoke(file: File, context: Context){
        sharedRepository.shareQrCode(file, context)
    }
}