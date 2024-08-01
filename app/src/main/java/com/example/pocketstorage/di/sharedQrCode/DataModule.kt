package com.example.pocketstorage.di.sharedQrCode

import android.content.Context
import com.example.pocketstorage.data.repository.PreferencesRepositoryImpl
import com.example.pocketstorage.data.repository.SharedRepositoryImpl
import com.example.pocketstorage.domain.repository.PreferencesRepository
import com.example.pocketstorage.domain.repository.SharedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideSharedQrCodeRepository(sharedRepositoryImpl: SharedRepositoryImpl): SharedRepository
}