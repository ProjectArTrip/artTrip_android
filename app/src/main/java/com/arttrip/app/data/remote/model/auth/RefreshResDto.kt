package com.arttrip.app.data.remote.model.auth

data class RefreshResDto(
    val newAccessToken: String,
    val refreshToken: String,
)
