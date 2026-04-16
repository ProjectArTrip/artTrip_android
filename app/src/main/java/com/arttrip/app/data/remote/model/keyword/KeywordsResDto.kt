package com.arttrip.app.data.remote.model.keyword

data class KeywordsResDto(
    val keywords: List<KeywordDto>,
)

data class KeywordDto(
    val keywordId: Int,
    val name: String,
    val type: String, // "GENRE" or "STYLE"
)
