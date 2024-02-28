package com.example.pocketstorage.domain.usecase


import com.example.pocketstorage.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

class GetAuthStateUseCase(private val authRepository: AuthRepository) {

    operator fun invoke() = authRepository.getAuthState()
}