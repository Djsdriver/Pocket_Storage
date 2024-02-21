package com.example.pocketstorage.presentation.ui.screens.auth.authorization

import com.example.pocketstorage.presentation.ui.screens.auth.AuthFlowScreenState
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType


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
