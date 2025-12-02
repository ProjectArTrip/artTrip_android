package com.arttrip.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.presentation.main.contract.AuthState
import com.arttrip.android.presentation.main.contract.MainIntent
import com.arttrip.android.presentation.main.contract.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MainState())
        val state: StateFlow<MainState> = _state.asStateFlow()

        init {
            viewModelScope.launch {
                val hasToken = tokenManager.hasTokens()
                _state.value =
                    _state.value.copy(
                        authState = if (hasToken) AuthState.LOGGED_IN else AuthState.LOGGED_OUT,
                    )
            }
        }

        fun dispatch(intent: MainIntent) {
            when (intent) {
                MainIntent.OnLoginSuccess -> {
                    _state.value = _state.value.copy(authState = AuthState.LOGGED_IN)
                }

                MainIntent.OnLogout -> {
                    _state.value = _state.value.copy(authState = AuthState.LOGGED_OUT)
                }
            }
        }
    }
