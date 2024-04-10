package com.example.pocketstorage.di.generationQRcode

import com.example.pocketstorage.data.repository.GenerationQRCodeRepositoryImpl
import com.example.pocketstorage.domain.repository.GenerationQrCodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGenerationQrCodeRepository(
        generationQRCodeRepositoryImpl: GenerationQRCodeRepositoryImpl
    ) : GenerationQrCodeRepository
}