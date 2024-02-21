package com.example.pocketstorage.presentation.ui.screens.auth.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.usecase.SignInUseCase
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

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

        //Log.d("fire", "${uiState.value.email} ${uiState.value.password}")
    }

    fun signInLoginAndPassword(email: String, password: String) =viewModelScope.launch {


            val authResult = signInUseCase.invoke(email = email, password = password)
            when (authResult) {
                is TaskResult.Success -> {
                    _signInState.update {
                        it.copy(email = email.trim(), password = password.trim())
                    }
                    _screenState.update {
                        it.copy(success = true)
                    }

                    Log.d("user", "${_screenState.value.success}")
                }

                is TaskResult.Error -> {
                    _screenState.value.error
                }
            }


    }
}