package com.arttrip.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.main.contract.AuthState
import com.arttrip.android.presentation.main.contract.MainIntent
import com.arttrip.android.presentation.main.contract.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    // private val authRepository: AuthRepository  // DataStore 등 DI로 주입
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        // TODO: 실제로는 DataStore / Token 체크 후 AuthState 결정
        viewModelScope.launch {
            // 예시: 토큰 있으면 LOGGED_IN, 없으면 LOGGED_OUT
            val hasToken = false
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
