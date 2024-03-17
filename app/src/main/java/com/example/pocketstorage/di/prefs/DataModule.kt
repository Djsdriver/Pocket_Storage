package com.example.pocketstorage.di.prefs

import android.content.Context
import com.example.pocketstorage.data.repository.PreferencesRepositoryImpl
import com.example.pocketstorage.domain.repository.PreferencesRepository
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
    fun providePreferencesRepository(@ApplicationContext context: Context): PreferencesRepository {
        return PreferencesRepositoryImpl(context)
    }
}