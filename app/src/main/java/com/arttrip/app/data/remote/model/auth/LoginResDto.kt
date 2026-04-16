package com.arttrip.app.data.remote.model.auth

data class LoginResDto(
    val accessToken: String,
    val refreshToken: String,
    val isFirstLogin: Boolean,
)
