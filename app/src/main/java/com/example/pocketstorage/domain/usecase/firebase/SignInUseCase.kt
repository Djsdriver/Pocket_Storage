package com.example.pocketstorage.domain.usecase.firebase

import com.example.pocketstorage.domain.repository.AuthRepository


class SignInUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}