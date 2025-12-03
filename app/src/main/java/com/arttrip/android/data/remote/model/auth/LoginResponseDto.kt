package com.arttrip.android.data.remote.model.auth

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val firstLogin: Boolean,
)
