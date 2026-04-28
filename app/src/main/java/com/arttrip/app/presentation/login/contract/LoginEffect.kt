package com.arttrip.app.presentation.login.contract

sealed interface LoginEffect {
    data object LaunchKakaoLogin : LoginEffect

    data object LaunchGoogleLogin : LoginEffect

    data object NavigateToNicknameStep : LoginEffect

    data object NavigateToTasteStep : LoginEffect

    data object NavigateToHome : LoginEffect

    /**
     * 공통 에러 토스트/스낵바
     */
    data class ShowError(
        val message: String,
    ) : LoginEffect
}
