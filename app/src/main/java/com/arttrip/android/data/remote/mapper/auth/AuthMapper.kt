package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.KeywordType
import com.arttrip.android.data.remote.model.auth.KeywordsResDto
import com.arttrip.android.data.remote.model.auth.LoginResDto
import com.arttrip.android.domain.model.auth.KeywordGroups
import com.arttrip.android.domain.model.auth.KeywordModel
import com.arttrip.android.domain.model.auth.LoginModel

fun LoginResDto.toDomain(): LoginModel =
    LoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isFirstLogin = firstLogin,
    )

fun List<KeywordsResDto>.toDomain(): KeywordGroups {
    val genres = mutableListOf<KeywordModel>()
    val styles = mutableListOf<KeywordModel>()

    for (dto in this) {
        val keyword =
            KeywordModel(
                id = dto.keywordId,
                name = dto.name,
            )

        when (KeywordType.valueOf(dto.type)) {
            KeywordType.GENRE -> genres += keyword
            KeywordType.STYLE -> styles += keyword
        }
    }

    return KeywordGroups(
        genres = genres,
        styles = styles,
    )
}
