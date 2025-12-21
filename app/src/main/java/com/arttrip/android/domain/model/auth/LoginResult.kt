package com.arttrip.android.domain.model.auth

data class LoginResult(
    val tokens: AuthTokens,
    val isFirstLogin: Boolean,
)
