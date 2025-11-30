package com.arttrip.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.login.SocialLoginUseCase
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
    ) : ViewModel() {
        private val _state = MutableStateFlow(LoginState())
        val state: StateFlow<LoginState> = _state

        private val _effect = MutableSharedFlow<LoginEffect>()
        val effect: SharedFlow<LoginEffect> = _effect

        fun onIntent(intent: LoginIntent) {
            when (intent) {
                LoginIntent.ClickKakaoLogin -> {
                    // 여기서는 그냥 "카카오 SDK 로그인 시작" 신호만 주고
                    // 실제 SDK 호출은 Activity/Composable 쪽에서 하는 패턴도 많음
                    // ex) _effect.emit(LoginEffect.LaunchKakaoLogin) 형태로도 가능
                }

                is LoginIntent.KakaoLoginSuccess -> {
                    loginWithIdToken(LoginProvider.KAKAO, intent.idToken)
                }

                is LoginIntent.KakaoLoginFailure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "카카오 로그인에 실패했어요. 다시 시도해 주세요.",
                        )
                    }
                    viewModelScope.launch {
                        _effect.emit(
                            LoginEffect.ShowError("카카오 로그인에 실패했어요."),
                        )
                    }
                }

                LoginIntent.DismissError -> {
                    _state.update {
                        it.copy(errorMessage = null)
                    }
                }

                LoginIntent.ClickGoogleLogin -> {
                    // TODO: 나중에 구현
                }
            }
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
                            val login = result.data // LoginModel

                            val effect =
                                if (login.isFirstLogin) {
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
