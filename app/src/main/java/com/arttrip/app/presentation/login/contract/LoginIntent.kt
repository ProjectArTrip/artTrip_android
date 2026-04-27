package com.arttrip.app.presentation.login.contract

sealed interface LoginIntent {
    data object ClickKakaoLogin : LoginIntent

    data class KakaoLoginSuccess(
        val idToken: String,
    ) : LoginIntent

    data class KakaoLoginFailure(
        val throwable: Throwable?,
    ) : LoginIntent

    data object ClickGoogleLogin : LoginIntent

    data class GoogleLoginSuccess(
        val authorizationCode: String,
    ) : LoginIntent

    data class GoogleLoginFailure(
        val throwable: Throwable?,
    ) : LoginIntent

    data object DismissError : LoginIntent
}
