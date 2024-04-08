package com.example.pocketstorage.di.scanner

import com.example.pocketstorage.domain.repository.PreferencesRepository
import com.example.pocketstorage.domain.repository.ScannerRepository
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.product.GetDataFromQRCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetDataFromQRCodeUseCase(scannerRepository: ScannerRepository)
            : GetDataFromQRCodeUseCase {
        return GetDataFromQRCodeUseCase(scannerRepository)
    }

}