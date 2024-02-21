package com.example.pocketstorage.di.auth


import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.domain.usecase.SignInUseCase
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository = authRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(repository = authRepository)
    }


}