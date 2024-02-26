package com.example.pocketstorage.data.repository

import android.util.Log
import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.CompletableDeferred
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
        if (authClient.currentUser != null) {
            return TaskResult.Error(ErrorType.AlreadySignedIn)
        }

        return try {
            return withContext(Dispatchers.IO) {

                val result = CompletableDeferred<TaskResult<Boolean>>()

                authClient.signInWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
                    // Вход в учетную запись прошел успешно
                    result.complete(TaskResult.Success(true))
                }.addOnFailureListener { exception ->
                    // Обработка ошибок входа в учетную запись
                    val errorType = when (exception) {
                        is FirebaseAuthInvalidUserException -> ErrorType.EmailNotFound
                        is FirebaseAuthInvalidCredentialsException -> ErrorType.InvalidCredentials
                        else -> ErrorType.Unknown
                    }
                    result.complete(TaskResult.Error(errorType))
                }

                result.await()
            }
        } catch (e: Exception) {
            TaskResult.Error(ErrorType.Unknown)
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