package com.arttrip.android.domain.model.auth

data class LoginModel(
    val accessToken: String,
    val refreshToken: String,
    val isFirstLogin: Boolean,
)
