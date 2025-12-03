package com.arttrip.android.presentation.login.contract

sealed interface LoginIntent {
    /**
     * 카카오 로그인 버튼 클릭
     */
    data object ClickKakaoLogin : LoginIntent

    /**
     * 구글 로그인 버튼 클릭
     */
    data object ClickGoogleLogin : LoginIntent

    /**
     * 카카오 SDK에서 로그인 성공해서 idToken을 넘겨준 경우
     */
    data class KakaoLoginSuccess(
        val idToken: String,
    ) : LoginIntent

    /**
     * 카카오 SDK에서 로그인 실패한 경우
     */
    data class KakaoLoginFailure(
        val throwable: Throwable?,
    ) : LoginIntent

    /**
     * 에러 다이얼로그/스낵바 닫기 등
     */
    data object DismissError : LoginIntent
}
