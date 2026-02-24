package com.arttrip.android.data.remote.model.auth

data class RefreshResDto(
    val newAccessToken: String,
    val refreshToken: String,
)
