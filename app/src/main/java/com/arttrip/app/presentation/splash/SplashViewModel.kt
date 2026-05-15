package com.arttrip.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.data.local.auth.OnboardingManager
import com.arttrip.app.data.local.auth.TokenManager
import com.arttrip.app.domain.model.auth.OnboardingStep
import com.arttrip.app.domain.model.maintenance.MaintenanceState
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.maintenance.GetMaintenanceUseCase
import com.arttrip.app.presentation.splash.contract.SplashEffect
import com.arttrip.app.presentation.splash.contract.SplashIntent
import com.arttrip.app.presentation.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val tokenManager: TokenManager,
        private val onboardingManager: OnboardingManager,
        private val getMaintenanceUseCase: GetMaintenanceUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(SplashState())
        val state: StateFlow<SplashState> = _state

        private val _effect = MutableSharedFlow<SplashEffect>()
        val effect: SharedFlow<SplashEffect> = _effect

        init {
            checkMaintenance()
        }

        fun onIntent(intent: SplashIntent) {
            when (intent) {
                SplashIntent.ExitApp -> {
                    viewModelScope.launch { _effect.emit(SplashEffect.ExitApp) }
                }
            }
        }

        private fun checkMaintenance() {
            viewModelScope.launch {
                getMaintenanceUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            val data = result.data
                            if (data.state == MaintenanceState.BLOCK) {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        showMaintenanceDialog = true,
                                        startAt = data.startAt,
                                        endAt = data.endAt,
                                    )
                                }
                            } else {
                                _state.update { it.copy(isLoading = false) }
                                checkAuthAndDecideRoute()
                            }
                        }
                        is ApiResult.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            checkAuthAndDecideRoute()
                        }
                    }
                }
            }
        }

        private fun checkAuthAndDecideRoute() {
            viewModelScope.launch {
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

                _state.update { it.copy(isLoading = false) }
                _effect.emit(SplashEffect.Navigate(target))
            }
        }
    }
