package com.example.pocketstorage.di.db

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pocketstorage.data.db.AppDatabase
import com.example.pocketstorage.data.repository.DatabaseRepositoryImpl
import com.example.pocketstorage.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }


    @Singleton
    @Provides
    fun provideDatabaseRepository(appDatabase: AppDatabase,@ApplicationContext context: Context): DatabaseRepository {
        return DatabaseRepositoryImpl(appDatabase,context)
    }
}