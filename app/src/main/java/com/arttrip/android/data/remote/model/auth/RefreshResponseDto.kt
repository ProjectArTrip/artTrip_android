package com.arttrip.android.data.remote.model.auth

data class RefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val firstLogin: Boolean,
)
