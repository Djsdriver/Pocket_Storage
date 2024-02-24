package com.example.pocketstorage.data.repository

import android.util.Log
import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authClient: FirebaseAuth): AuthRepository {
    override suspend fun signUp(email: String, password: String): TaskResult<Boolean> {
        if (authClient.currentUser != null) return TaskResult.Error(ErrorType.AlreadySignedUp)

        Log.d("fire", "${authClient.currentUser}")

        return try {
            withContext(Dispatchers.IO) {
                authClient.createUserWithEmailAndPassword(email, password).await()
                TaskResult.Success(true)
            }
        } catch (e: Exception) {
            TaskResult.Error(getAuthError(e.message))
        }
    }

    override suspend fun signIn(email: String, password: String): TaskResult<Boolean> {
        if (authClient.currentUser != null) return TaskResult.Error(ErrorType.AlreadySignedIn)

        return try {
            withContext(Dispatchers.IO) {
                authClient.signInWithEmailAndPassword(email, password).await()
                TaskResult.Success(true)
            }
        } catch (e: Exception) {
            TaskResult.Error(getAuthError(e.message))
        }
    }

    /*override suspend fun sendNewPassword(email: String): TaskResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getAuthState(coroutineScope: CoroutineScope): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun logOut() {
        TODO("Not yet implemented")
    }*/

    private fun getAuthError(message: String?): ErrorType {
        return message?.let {
            ErrorType.AuthFailed(message)
        } ?: ErrorType.Unknown
    }
}