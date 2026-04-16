package com.arttrip.app.data.remote.model.auth

data class LoginReqDto(
    val provider: String,
    val idToken: String,
)
