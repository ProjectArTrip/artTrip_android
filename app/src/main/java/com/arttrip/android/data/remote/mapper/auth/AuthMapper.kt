package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.LoginResponseDto
import com.arttrip.android.domain.model.auth.LoginModel

fun LoginResponseDto.toDomain(): LoginModel =
    LoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isFirstLogin = firstLogin,
    )
