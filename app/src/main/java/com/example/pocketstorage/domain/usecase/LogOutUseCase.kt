package com.example.pocketstorage.domain.usecase

import com.example.pocketstorage.domain.repository.AuthRepository


class LogOutUseCase(private val repository: AuthRepository) {

    operator fun invoke() = repository.logOut()
}