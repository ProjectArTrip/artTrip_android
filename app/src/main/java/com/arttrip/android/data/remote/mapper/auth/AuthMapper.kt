package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.KeywordType
import com.arttrip.android.data.remote.model.auth.KeywordsResDto
import com.arttrip.android.data.remote.model.auth.LoginResDto
import com.arttrip.android.domain.model.auth.AuthTokens
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.userkeyword.KeywordGroups
import com.arttrip.android.domain.model.userkeyword.KeywordModel

fun LoginResDto.toDomain(): LoginResult =
    LoginResult(
        tokens =
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
            ),
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
