package com.example.pocketstorage.di.prefs

import com.example.pocketstorage.domain.repository.PreferencesRepository
import com.example.pocketstorage.domain.usecase.prefs.GetLocationIdFromDataStorageUseCase
import com.example.pocketstorage.domain.usecase.prefs.SaveLocationIdToDataStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetLocationIdFromDataStorageUseCase(preferencesRepository: PreferencesRepository)
            : GetLocationIdFromDataStorageUseCase {
        return GetLocationIdFromDataStorageUseCase(preferencesRepository)
    }

    @Provides
    fun provideSaveLocationIdToDataStorageUseCase(preferencesRepository: PreferencesRepository)
            : SaveLocationIdToDataStorageUseCase {
        return SaveLocationIdToDataStorageUseCase(preferencesRepository)
    }
}