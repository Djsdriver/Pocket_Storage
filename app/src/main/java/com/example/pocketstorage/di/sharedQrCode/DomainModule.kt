package com.example.pocketstorage.di.sharedQrCode


import com.example.pocketstorage.domain.repository.SharedRepository
import com.example.pocketstorage.domain.usecase.sharedQrCode.SharedQrCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideSharedQrCodeCodeUseCase(sharedRepository: SharedRepository)
            : SharedQrCodeUseCase {
        return SharedQrCodeUseCase(sharedRepository)
    }

}