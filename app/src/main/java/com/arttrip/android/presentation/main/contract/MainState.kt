package com.arttrip.android.presentation.main.contract

enum class AuthState {
    UNKNOWN, // 앱 켰을 때 토큰 체크 중
    LOGGED_OUT, // 로그인 필요
    LOGGED_IN, // 로그인 완료
}

data class MainState(
    val authState: AuthState = AuthState.UNKNOWN,
)
