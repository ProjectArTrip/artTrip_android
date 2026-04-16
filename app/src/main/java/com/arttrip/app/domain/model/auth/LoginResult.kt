package com.arttrip.app.domain.model.auth

data class LoginResult(
    val tokens: AuthTokens,
    val isFirstLogin: Boolean,
)
