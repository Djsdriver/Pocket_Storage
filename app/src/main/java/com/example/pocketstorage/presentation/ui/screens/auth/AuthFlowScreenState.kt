package com.example.pocketstorage.presentation.ui.screens.auth

data class AuthFlowScreenState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: ErrorType

) {
    companion object {
        val INITIAL = AuthFlowScreenState(loading = false, success = false, error = ErrorType.Unknown)
    }
}

/*
sealed class AuthFlowScreenState {
    data object Loading : AuthFlowScreenState()
    data object Success : AuthFlowScreenState()
    data class Error(val errorType: ErrorType) : AuthFlowScreenState()
}*/
