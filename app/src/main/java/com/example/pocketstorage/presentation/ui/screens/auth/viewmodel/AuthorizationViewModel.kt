package com.example.pocketstorage.presentation.ui.screens.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.usecase.SignInUseCase
import com.example.pocketstorage.presentation.ui.screens.auth.ErrorType
import com.example.pocketstorage.presentation.ui.screens.auth.SignInFlowScreenState
import com.example.pocketstorage.presentation.ui.screens.auth.SignInUiState
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.example.pocketstorage.utils.SnackbarManager
import com.example.pocketstorage.utils.isValidEmail
import com.example.pocketstorage.utils.isValidPassword
import com.example.pocketstorage.utils.passwordMatches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(SignInFlowScreenState.INITIAL)
    val screenState = _screenState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInUiState())
    val signInState = _signInState.asStateFlow()


    fun onEmailChange(newValue: String) {
        _signInState.update {
            it.copy(email = newValue)
        }

    }

    fun onPasswordChange(newValue: String) {
        _signInState.update {
            it.copy(password = newValue)
        }

    }

    fun signInLoginAndPassword(email: String, password: String) = viewModelScope.launch {
        _screenState.update {
            it.copy(loading = true)
        }


        val authResult = signInUseCase.invoke(email = email, password = password)
        when (authResult) {
            is TaskResult.Success -> {
                _signInState.update {
                    it.copy(email = email.trim(), password = password.trim())
                }
                _screenState.update {
                    it.copy(
                        success = true
                    )
                }
            }

            is TaskResult.Error -> {
                when (authResult.errorType) {
                    ErrorType.AlreadySignedIn -> {
                        SnackbarManager.showMessage(R.string.email_already_use)
                    }

                    ErrorType.EmailNotFound -> {
                        SnackbarManager.showMessage(R.string.email_not_found)
                    }

                    ErrorType.FirebaseNetworkException -> {
                        SnackbarManager.showMessage(R.string.network_error)
                    }

                    else -> {
                        SnackbarManager.showMessage(R.string.email_error)
                    }
                }
                _screenState.update {
                    it.copy(error = authResult.errorType)
                }
            }
        }
    }


}
