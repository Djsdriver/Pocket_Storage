package com.example.pocketstorage.di.sharedQrCode

import android.content.Context
import com.example.pocketstorage.data.repository.PreferencesRepositoryImpl
import com.example.pocketstorage.data.repository.SharedRepositoryImpl
import com.example.pocketstorage.domain.repository.PreferencesRepository
import com.example.pocketstorage.domain.repository.SharedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSharedQrCodeRepository(@ApplicationContext context: Context): SharedRepository {
        return SharedRepositoryImpl(context)
    }
}