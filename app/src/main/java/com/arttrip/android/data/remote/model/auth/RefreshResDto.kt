package com.arttrip.android.data.remote.model.auth

data class RefreshResDto(
    val accessToken: String,
    val refreshToken: String,
    val firstLogin: Boolean,
)
