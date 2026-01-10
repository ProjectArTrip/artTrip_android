package com.arttrip.android.data.remote.mapper.auth

import com.arttrip.android.data.remote.model.auth.KeywordType
import com.arttrip.android.data.remote.model.auth.KeywordsResDto
import com.arttrip.android.data.remote.model.auth.LoginResDto
import com.arttrip.android.domain.model.auth.AuthTokens
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.usertaste.TasteGroupModel
import com.arttrip.android.domain.model.usertaste.TasteModel

fun LoginResDto.toDomain(): LoginResult =
    LoginResult(
        tokens =
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
            ),
        isFirstLogin = firstLogin,
    )

fun List<KeywordsResDto>.toDomain(): TasteGroupModel {
    val genres = mutableListOf<TasteModel>()
    val styles = mutableListOf<TasteModel>()

    for (dto in this) {
        val taste =
            TasteModel(
                id = dto.keywordId,
                name = dto.name,
            )

        when (KeywordType.valueOf(dto.type)) {
            KeywordType.GENRE -> genres += taste
            KeywordType.STYLE -> styles += taste
        }
    }

    return TasteGroupModel(
        genres = genres,
        styles = styles,
    )
}
