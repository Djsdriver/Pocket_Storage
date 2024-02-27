package com.example.pocketstorage.data.repository

import android.util.Log
import com.example.pocketstorage.domain.repository.AuthRepository
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
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
            withContext(Dispatchers.IO) {authClient.signInWithEmailAndPassword(email, password).await()
                TaskResult.Success(true)
            }
        } catch (e: FirebaseAuthException) {
            Log.d("error","${e.message}")
            TaskResult.Error(ErrorType.EmailNotFound)
        } catch (e: FirebaseNetworkException){
            TaskResult.Error(ErrorType.FirebaseNetworkException)
        }

    }

    override suspend fun sendNewPassword(email: String): TaskResult<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                authClient.sendPasswordResetEmail(email).await()
                TaskResult.Success(true)
            }
        } catch (e: Exception) {
            TaskResult.Error(getAuthError(e.message))
        }
    }

    override fun getAuthState(coroutineScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        authClient.addAuthStateListener(authStateListener)
        awaitClose {
            authClient.removeAuthStateListener(authStateListener)
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), authClient.currentUser == null)

    override fun logOut() {
        authClient.signOut()
    }

    private fun getAuthError(message: String?): ErrorType {
        return message?.let {
            ErrorType.AuthFailed(message)
        } ?: ErrorType.Unknown
    }
}