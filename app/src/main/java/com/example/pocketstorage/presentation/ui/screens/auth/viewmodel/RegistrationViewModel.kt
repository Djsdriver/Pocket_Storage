package com.example.pocketstorage.presentation.ui.screens.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.utils.SnackbarManager
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import com.example.pocketstorage.presentation.ui.screens.auth.AuthFlowScreenState
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType
import com.example.pocketstorage.presentation.ui.screens.auth.SignUpUiState
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.example.pocketstorage.utils.isValidEmail
import com.example.pocketstorage.utils.isValidPassword
import com.example.pocketstorage.utils.passwordMatches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.pocketstorage.R.string as AppText
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUp: SignUpUseCase
) : ViewModel() {


    private val _screenState = MutableStateFlow(AuthFlowScreenState.INITIAL)
    val screenState = _screenState.asStateFlow()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()



    fun onEmailChange(newValue: String) {
        _uiState.update {
            it.copy(email = newValue)
        }
        Log.d("fire", "${uiState.value.email} ${uiState.value.password}")
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(password = newValue)
        }

        Log.d("fire", "${uiState.value.email} ${uiState.value.password}")
    }
    fun onRepeatPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(repeatPassword = newValue)
        }
    }

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _screenState.update { currentState ->
            currentState.copy(
                loading = true)
        }
            val authResult = signUp(email, password)
            when (authResult) {
                is TaskResult.Success -> {
                    _uiState.update {
                        it.copy(email = email.trim(), password = password.trim())
                    }
                    _screenState.update {
                        it.copy(success = true)
                    }
                }
                is TaskResult.Error -> {
                    _screenState.update {
                        it.copy(error = ErrorType.AuthFailed("Ошибка регистрации"))
                    }
                }
            }
        }


    fun onSignUpClick() {
        if (!_uiState.value.email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!_uiState.value.password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!uiState.value.password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        if (!_uiState.value.password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}\$".toRegex())) {
            SnackbarManager.showMessage(AppText.password_format_error)
            return
        }
    }



}