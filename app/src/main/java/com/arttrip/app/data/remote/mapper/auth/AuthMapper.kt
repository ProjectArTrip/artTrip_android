package com.arttrip.app.data.remote.mapper.auth

import com.arttrip.app.data.remote.model.auth.LoginResDto
import com.arttrip.app.domain.model.auth.AuthTokens
import com.arttrip.app.domain.model.auth.LoginResult

fun LoginResDto.toDomain(): LoginResult =
    LoginResult(
        tokens =
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
            ),
        isFirstLogin = isFirstLogin,
    )
