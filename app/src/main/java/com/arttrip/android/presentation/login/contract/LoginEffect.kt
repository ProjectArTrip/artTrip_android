package com.arttrip.android.presentation.login.contract

sealed interface LoginEffect {
    data object LaunchKakaoLogin : LoginEffect

    data object LaunchGoogleLogin : LoginEffect

    data object NavigateToIntro : LoginEffect

    data object NavigateToHome : LoginEffect

    /**
     * 공통 에러 토스트/스낵바
     */
    data class ShowError(
        val message: String,
    ) : LoginEffect
}
