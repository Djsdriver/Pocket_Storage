package com.example.pocketstorage.presentation.ui.screens.auth

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)