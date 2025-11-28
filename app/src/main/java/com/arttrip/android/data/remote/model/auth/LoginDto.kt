package com.arttrip.android.data.remote.model.auth

// 요청 바디
data class LoginRequest(
    val idToken: String,
)

// 응답 바디
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    // 첫 로그인 여부 플래그
    val isFirstLogin: Boolean,
)