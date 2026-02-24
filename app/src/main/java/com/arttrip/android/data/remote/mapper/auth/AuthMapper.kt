package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.LoginResDto
import com.arttrip.android.domain.model.auth.AuthTokens
import com.arttrip.android.domain.model.auth.LoginResult

fun LoginResDto.toDomain(): LoginResult =
    LoginResult(
        tokens =
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
            ),
        isFirstLogin = isFirstLogin,
    )
