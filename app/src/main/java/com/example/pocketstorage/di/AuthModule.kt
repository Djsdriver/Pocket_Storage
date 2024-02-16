package com.example.pocketstorage.di

import com.example.pocketstorage.data.repository.AuthRepositoryImpl
import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideRepository(): AuthRepository {
        return AuthRepositoryImpl(auth())
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun auth(): FirebaseAuth = Firebase.auth





}