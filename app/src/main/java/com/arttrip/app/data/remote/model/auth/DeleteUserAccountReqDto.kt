package com.arttrip.app.data.remote.model.auth

data class DeleteUserAccountReqDto(
    val accessToken: String,
    val refreshToken: String,
)
