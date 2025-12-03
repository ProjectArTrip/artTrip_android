package com.arttrip.android.data.remote.model.auth

data class LoginRequestDto(
    val provider: String,
    val idToken: String,
)
