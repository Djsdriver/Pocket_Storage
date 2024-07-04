package com.example.pocketstorage.domain.usecase.firebase

import com.example.pocketstorage.domain.repository.AuthRepository


class LogOutUseCase(private val repository: AuthRepository) {

    operator fun invoke() = repository.logOut()
}