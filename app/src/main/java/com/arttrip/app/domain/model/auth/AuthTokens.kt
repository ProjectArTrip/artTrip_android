package com.arttrip.app.domain.model.auth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
)
