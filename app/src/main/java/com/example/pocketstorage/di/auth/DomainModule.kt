package com.example.pocketstorage.di.auth


import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository
import com.example.pocketstorage.domain.usecase.firebase.GetAuthStateUseCase
import com.example.pocketstorage.domain.usecase.firebase.LogOutUseCase
import com.example.pocketstorage.domain.usecase.firebase.SignInUseCase
import com.example.pocketstorage.domain.usecase.firebase.SignUpUseCase
import com.example.pocketstorage.domain.usecase.firebase.CreateUserAndLinkDatabaseUseCase
import com.example.pocketstorage.domain.usecase.firebase.GetDataFromFirebaseUseCase
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

    @Provides
    fun provideGetAuthUseCase(authRepository: AuthRepository): GetAuthStateUseCase {
        return GetAuthStateUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideLogOutUseCase(authRepository: AuthRepository): LogOutUseCase {
        return LogOutUseCase(repository = authRepository)
    }

    @Provides
    fun provideCreateDatabaseRealtimeUseCase(databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository): CreateUserAndLinkDatabaseUseCase {
        return CreateUserAndLinkDatabaseUseCase(databaseFirebaseRealtimeRepository =  databaseFirebaseRealtimeRepository)
    }

    @Provides
    fun provideGetDataFromFirebaseUseCase(databaseFirebaseRealtimeRepository: DatabaseFirebaseRealtimeRepository): GetDataFromFirebaseUseCase {
        return GetDataFromFirebaseUseCase(databaseFirebaseRealtimeRepository =  databaseFirebaseRealtimeRepository)
    }




}