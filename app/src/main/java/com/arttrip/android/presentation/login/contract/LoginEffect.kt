package com.arttrip.android.presentation.login.contract


sealed interface LoginEffect {

    /**
     * Intro 화면으로 이동
     */
    data object NavigateToIntro : LoginEffect

    /**
     * Home 화면으로 이동
     */
    data object NavigateToHome : LoginEffect

    /**
     * 공통 에러 토스트/스낵바
     */
    data class ShowError(
        val message: String,
    ) : LoginEffect
}