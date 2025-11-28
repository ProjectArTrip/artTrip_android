package com.arttrip.android.domain.model.auth


data class LoginResult(
    val accessToken: String,
    val refreshToken: String?,
    val isFirstLogin: Boolean,
)