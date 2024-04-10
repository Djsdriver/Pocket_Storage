package com.example.pocketstorage.di.generationQRcode

import com.example.pocketstorage.domain.repository.GenerationQrCodeRepository
import com.example.pocketstorage.domain.usecase.product.GenerationQrCodeProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGenerationQRCodeUseCase(generationQrCodeRepository: GenerationQrCodeRepository)
            : GenerationQrCodeProductUseCase {
        return GenerationQrCodeProductUseCase(generationQrCodeRepository)
    }

}