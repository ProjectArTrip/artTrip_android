package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.KeywordType
import com.arttrip.android.data.remote.model.auth.KeywordsResponseDto
import com.arttrip.android.data.remote.model.auth.LoginResponseDto
import com.arttrip.android.domain.model.auth.KeywordModel
import com.arttrip.android.domain.model.auth.LoginModel

fun LoginResponseDto.toDomain(): LoginModel =
    LoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isFirstLogin = firstLogin,
    )

fun KeywordsResponseDto.toDomain(): KeywordModel =
    KeywordModel(
        id = keywordId,
        name = name,
        type = KeywordType.valueOf(type), // "GENRE" / "STYLE"
    )

fun List<KeywordsResponseDto>.toDomain(): List<KeywordModel> = map { it.toDomain() }
