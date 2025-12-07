package com.arttrip.android.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.presentation.main.contract.AuthState
import com.arttrip.android.presentation.main.contract.MainIntent
import com.arttrip.android.presentation.main.contract.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val tokenManager: TokenManager,
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MainState())
        val state: StateFlow<MainState> = _state.asStateFlow()

        val logoutEvents = sessionManager.logoutEvents

        init {
            viewModelScope.launch {
                val hasToken = tokenManager.hasTokens()
                val authState =
                    if (hasToken) AuthState.LOGGED_IN else AuthState.LOGGED_OUT

                Log.d(
                    TAG,
                    "init: hasToken=$hasToken → authState=$authState",
                )

                _state.value = _state.value.copy(authState = authState)
            }
        }

        fun onIntent(intent: MainIntent) {
            when (intent) {
                MainIntent.OnLoginSuccess -> {
                    _state.value = _state.value.copy(authState = AuthState.LOGGED_IN)
                }

                MainIntent.OnLogout -> {
                    _state.value = _state.value.copy(authState = AuthState.LOGGED_OUT)
                }
            }
        }

        companion object {
            private const val TAG = "MainViewModel"
        }
    }
