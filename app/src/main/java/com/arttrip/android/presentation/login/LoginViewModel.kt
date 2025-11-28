package com.arttrip.android.presentation.login

import com.arttrip.android.domain.usecase.user.KakaoLoginUseCase

class LoginViewModel(
    private val kakaoLoginUseCase: KakaoLoginUseCase,   // idToken → 서버 로그인
) : ViewModel() {

    // StateFlow / SharedFlow 구조는 프로젝트 베이스에 맞춰서
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
                loginWithKakaoIdToken(intent.idToken)
            }

            is LoginIntent.KakaoLoginFailure -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "카카오 로그인에 실패했어요. 다시 시도해 주세요."
                    )
                }
                viewModelScope.launch {
                    _effect.emit(
                        LoginEffect.ShowError("카카오 로그인에 실패했어요.")
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

    private fun loginWithKakaoIdToken(idToken: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isKakaoLoginEnabled = false) }

            runCatching {
                kakaoLoginUseCase(idToken)
            }.onSuccess { result ->
                _state.update { it.copy(isLoading = false, isKakaoLoginEnabled = true) }

                // result 안에 isFirstLogin 이 있다고 가정
                val effect = if (result.isFirstLogin) {
                    LoginEffect.NavigateToIntro
                } else {
                    LoginEffect.NavigateToHome
                }

                _effect.emit(effect)
            }.onFailure { throwable ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isKakaoLoginEnabled = true,
                        errorMessage = throwable.message ?: "로그인에 실패했습니다."
                    )
                }
                _effect.emit(
                    LoginEffect.ShowError("로그인에 실패했습니다.")
                )
            }
        }
    }
}
