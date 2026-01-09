package com.arttrip.android.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.navigation.app.AppRoute
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.presentation.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(SplashState())
        val state: StateFlow<SplashState> = _state

        init {
            checkAuthAndDecideRoute()
        }

        private fun checkAuthAndDecideRoute() {
            viewModelScope.launch {
                // 스플래쉬가 너무 순식간에 안 사라지도록 약간의 딜레이
                delay(1000L)

                val hasValidToken = tokenManager.hasTokens()

                val target =
                    if (hasValidToken) {
                        AppRoute.MAIN
                    } else {
                        AppRoute.LOGIN
                    }

                _state.value =
                    SplashState(
                        isLoading = false,
                        targetRoute = target,
                    )
            }
        }

        companion object {
            private const val TAG = "SplashViewModel"
        }
    }
