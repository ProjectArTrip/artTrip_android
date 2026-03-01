package com.arttrip.android.data.remote.mapper.keyword

import com.arttrip.android.data.remote.model.keyword.KeywordType
import com.arttrip.android.data.remote.model.keyword.KeywordsResDto
import com.arttrip.android.domain.model.usertaste.Taste
import com.arttrip.android.domain.model.usertaste.TasteGroup

fun KeywordsResDto.toDomain(): TasteGroup {
    val genres = mutableListOf<Taste>()
    val styles = mutableListOf<Taste>()

    for (dto in keywords) {
        val taste =
            Taste(
                id = dto.keywordId,
                name = dto.name,
            )

        when (dto.type.toKeywordTypeOrNull()) {
            KeywordType.GENRE -> genres += taste
            KeywordType.STYLE -> styles += taste
            null -> {}
        }
    }

    return TasteGroup(
        genres = genres,
        styles = styles,
    )
}

private fun String.toKeywordTypeOrNull(): KeywordType? = runCatching { KeywordType.valueOf(this) }.getOrNull()
