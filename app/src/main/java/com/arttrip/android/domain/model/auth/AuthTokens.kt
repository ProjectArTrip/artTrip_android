package com.arttrip.android.domain.model.auth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
)
