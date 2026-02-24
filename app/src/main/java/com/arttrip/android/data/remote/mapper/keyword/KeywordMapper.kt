package com.arttrip.android.data.remote.mapper.keyword

import com.arttrip.android.data.remote.model.keyword.KeywordType
import com.arttrip.android.data.remote.model.keyword.KeywordsResDto
import com.arttrip.android.domain.model.usertaste.Taste
import com.arttrip.android.domain.model.usertaste.TasteGroup

fun List<KeywordsResDto>.toDomain(): TasteGroup {
    val genres = mutableListOf<Taste>()
    val styles = mutableListOf<Taste>()

    for (dto in this) {
        val taste =
            Taste(
                id = dto.keywordId,
                name = dto.name,
            )

        when (KeywordType.valueOf(dto.type)) {
            KeywordType.GENRE -> genres += taste
            KeywordType.STYLE -> styles += taste
        }
    }

    return TasteGroup(
        genres = genres,
        styles = styles,
    )
}
