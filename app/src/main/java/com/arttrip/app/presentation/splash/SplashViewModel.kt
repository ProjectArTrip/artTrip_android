package com.arttrip.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.data.local.auth.OnboardingManager
import com.arttrip.app.data.local.auth.TokenManager
import com.arttrip.app.domain.model.auth.OnboardingStep
import com.arttrip.app.presentation.splash.contract.SplashState
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
        private val onboardingManager: OnboardingManager,
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
                    if (!hasValidToken) {
                        AppRoute.LOGIN
                    } else {
                        when (onboardingManager.get()) {
                            OnboardingStep.NICKNAME -> AppRoute.INTRO_NICKNAME
                            OnboardingStep.TASTE -> AppRoute.INTRO_TASTE
                            else -> AppRoute.MAIN
                        }
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
