package com.example.pocketstorage.presentation.ui.screens.auth

sealed class TaskResult<out T> {
    data class Success<out T>(val data: T?) : TaskResult<T>()
    data class Error(val errorType: ErrorType) : TaskResult<Nothing>()
}