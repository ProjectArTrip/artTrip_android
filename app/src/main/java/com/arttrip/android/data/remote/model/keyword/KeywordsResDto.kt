package com.arttrip.android.data.remote.model.keyword

data class KeywordsResDto(
 val keywords : List<KeywordDto>
)

data class KeywordDto(
    val keywordId: Int,
    val name: String,
    val type: String, // "GENRE" or "STYLE"
)