package com.arttrip.app.data.remote.mapper.keyword

import com.arttrip.app.data.remote.model.keyword.KeywordType
import com.arttrip.app.data.remote.model.keyword.KeywordsResDto
import com.arttrip.app.domain.model.usertaste.Taste
import com.arttrip.app.domain.model.usertaste.TasteGroup

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

fun KeywordsResDto.toRecommendList(): List<Taste> =
    keywords.map { dto ->
        Taste(
            id = dto.keywordId,
            name = dto.name,
        )
    }

private fun String.toKeywordTypeOrNull(): KeywordType? = runCatching { KeywordType.valueOf(this) }.getOrNull()
