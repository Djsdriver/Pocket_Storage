package com.example.pocketstorage.domain.usecase.firebase

import com.example.pocketstorage.domain.repository.AuthRepository
import javax.inject.Inject


class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}