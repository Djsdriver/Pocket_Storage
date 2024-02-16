package com.example.pocketstorage.presentation.ui.screens.auth.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.data.repository.AuthRepositoryImpl
import com.example.pocketstorage.domain.usecase.SignUpUseCase
import com.example.pocketstorage.presentation.ui.screens.auth.AuthFlowScreenState
import com.example.pocketstorage.presentation.ui.screens.auth.LoginUiState
import com.example.pocketstorage.presentation.ui.screens.auth.TaskResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    var uiState = mutableStateOf(LoginUiState())
        private set


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _screenState.update { currentState ->
            currentState.copy(
                loading = true)
        }

        val authResult = signUp(email, password)
        when (authResult) {
            is TaskResult.Success -> {
                _screenState.value.success
                _email.value=email
                _password.value=password
            }
            is TaskResult.Error -> _screenState.value.error
        }
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }


}