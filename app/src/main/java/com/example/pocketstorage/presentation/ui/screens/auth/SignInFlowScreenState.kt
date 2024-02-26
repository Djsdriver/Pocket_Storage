package com.example.pocketstorage.presentation.ui.screens.auth

data class SignInFlowScreenState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: ErrorType
)
{
    companion object {
        val INITIAL = SignInFlowScreenState(loading = false, success = false, error = ErrorType.Unknown)
    }
}
