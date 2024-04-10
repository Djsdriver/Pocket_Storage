package com.example.pocketstorage.di.generationQRcode

import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object GenerationModule {


    @ViewModelScoped
    @Provides
    fun provideBarcodeEncoder(): BarcodeEncoder {
        return BarcodeEncoder()
    }

}