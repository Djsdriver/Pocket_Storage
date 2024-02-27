package com.example.pocketstorage.domain.repository

import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String): TaskResult<Boolean>

    suspend fun signIn(email: String, password: String): TaskResult<Boolean>

    suspend fun sendNewPassword(email: String): TaskResult<Boolean>

    fun getAuthState(coroutineScope: CoroutineScope): Flow<Boolean>

    fun logOut()
}