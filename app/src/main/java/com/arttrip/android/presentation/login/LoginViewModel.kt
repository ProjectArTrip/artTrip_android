package com.arttrip.android.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.auth.SocialLoginUseCase
import com.arttrip.android.presentation.login.contract.LoginEffect
import com.arttrip.android.presentation.login.contract.LoginIntent
import com.arttrip.android.presentation.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val socialLoginUseCase: SocialLoginUseCase,
        private val tokenManager: TokenManager,
    ) : ViewModel() {
        companion object {
            private const val TAG = "LoginViewModel"
        }

        private val _state = MutableStateFlow(LoginState())
        val state: StateFlow<LoginState> = _state

        private val _effect = MutableSharedFlow<LoginEffect>()
        val effect: SharedFlow<LoginEffect> = _effect

        fun onIntent(intent: LoginIntent) {
            when (intent) {
                LoginIntent.ClickKakaoLogin -> {
                    _state.update { state -> reduce(state, intent) }
                    viewModelScope.launch {
                        _effect.emit(LoginEffect.LaunchKakaoLogin)
                    }
                }

                is LoginIntent.KakaoLoginSuccess -> {
                    loginWithIdToken(LoginProvider.KAKAO, intent.idToken)
                }

                is LoginIntent.KakaoLoginFailure -> {
                    intent.throwable?.let { Log.e(TAG, "Kakao login failed", it) }
                    _state.update { state -> reduce(state, intent) }
                    viewModelScope.launch {
                        _effect.emit(LoginEffect.ShowError("카카오 로그인에 실패했어요. 다시 시도해 주세요."))
                    }
                }

                LoginIntent.ClickGoogleLogin -> {
                    _state.update { state -> reduce(state, intent) }
                    viewModelScope.launch {
                        _effect.emit(LoginEffect.LaunchGoogleLogin)
                    }
                }

                is LoginIntent.GoogleLoginSuccess -> {
                    loginWithIdToken(LoginProvider.GOOGLE, intent.idToken)
                }

                is LoginIntent.GoogleLoginFailure -> {
                    intent.throwable?.let { Log.e(TAG, "Google login failed", it) }
                    _state.update { state -> reduce(state, intent) }
                    viewModelScope.launch {
                        _effect.emit(LoginEffect.ShowError("구글 로그인에 실패했어요. 다시 시도해 주세요."))
                    }
                }

                LoginIntent.DismissError -> {
                    _state.update { state -> reduce(state, intent) }
                }
            }
        }

        private fun reduce(
            state: LoginState,
            intent: LoginIntent,
        ): LoginState =
            when (intent) {
                LoginIntent.ClickKakaoLogin, LoginIntent.ClickGoogleLogin ->
                    state.copy(
                        isLoading = true,
                        errorMessage = null,
                    )

                is LoginIntent.KakaoLoginFailure ->
                    state.copy(
                        isLoading = false,
                        errorMessage = "카카오 로그인에 실패했어요. 다시 시도해 주세요.",
                    )
                is LoginIntent.GoogleLoginFailure ->
                    state.copy(
                        isLoading = false,
                        errorMessage = "구글 로그인에 실패했어요. 다시 시도해 주세요.",
                    )

                LoginIntent.DismissError ->
                    state.copy(
                        errorMessage = null,
                    )

                is LoginIntent.KakaoLoginSuccess,
                is LoginIntent.GoogleLoginSuccess,
                -> state
            }

        private fun loginWithIdToken(
            provider: LoginProvider,
            idToken: String,
        ) {
            viewModelScope.launch {
                socialLoginUseCase(provider, idToken).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null,
                                )
                            }
                        }

                        is ApiResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                            val data = result.data
                            val tokens = data.tokens
                            tokenManager.saveTokens(tokens)

                            val effect =
                                if (data.isFirstLogin) {
                                    LoginEffect.NavigateToIntro
                                } else {
                                    LoginEffect.NavigateToHome
                                }
                            _effect.emit(effect)
                        }

                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "로그인에 실패했습니다.",
                                )
                            }
                            _effect.emit(LoginEffect.ShowError("로그인에 실패했습니다."))
                        }
                    }
                }
            }
        }
    }
