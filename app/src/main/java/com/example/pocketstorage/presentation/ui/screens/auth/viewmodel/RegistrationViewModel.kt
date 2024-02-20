package com.example.pocketstorage.presentation.ui.screens.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import com.example.pocketstorage.presentation.ui.screens.auth.AuthFlowScreenState
import com.example.pocketstorage.presentation.ui.screens.auth.SignUpUiState
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
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


        if (_uiState.value.password==_uiState.value.repeatPassword){
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
                    _screenState.value.error
                }
            }
        }
    }



}