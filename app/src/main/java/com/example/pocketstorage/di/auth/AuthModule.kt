package com.example.pocketstorage.di.auth

import com.example.pocketstorage.data.db.AppDatabase
import com.example.pocketstorage.data.repository.AuthRepositoryImpl
import com.example.pocketstorage.data.repository.DatabaseFirebaseRealtimeRepositoryImpl
import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    //если использовать эти закомментированный кусочек кода, тогда можно убрать метод из файла ServiceModule
    @Provides
    @Singleton
    fun provideRepository(): AuthRepository {
        return AuthRepositoryImpl(provideAuth())
    }

    @Provides
    @Singleton
    fun provideDatabaseRealtimeRepository(appDatabase: AppDatabase): DatabaseFirebaseRealtimeRepository {
        return DatabaseFirebaseRealtimeRepositoryImpl(provideDatabaseRealtimeInstance(), provideAuth(),appDatabase)
    }

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideDatabaseRealtimeInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideDatabaseFirestoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()


}
