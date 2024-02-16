package com.example.pocketstorage.di

import com.example.pocketstorage.data.repository.AuthRepositoryImpl
import com.example.pocketstorage.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds
  abstract fun provideAccountService(impl: AuthRepositoryImpl): AuthRepository

}