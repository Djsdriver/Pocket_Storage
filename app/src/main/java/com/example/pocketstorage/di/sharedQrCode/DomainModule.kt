package com.example.pocketstorage.di.sharedQrCode


import com.example.pocketstorage.domain.repository.SharedRepository
import com.example.pocketstorage.domain.usecase.sharedQrCode.SaveQrCodeUseCase
import com.example.pocketstorage.domain.usecase.sharedQrCode.ShareQrCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideShareQrCodeUseCase(sharedRepository: SharedRepository)
            : ShareQrCodeUseCase {
        return ShareQrCodeUseCase(sharedRepository)
    }
    @Provides
    fun provideSaveQrCodeUseCase(sharedRepository: SharedRepository)
            : SaveQrCodeUseCase {
        return SaveQrCodeUseCase(sharedRepository)
    }

}