package com.arttrip.android.data.remote.model.keyword

data class KeywordsResDto(
    val keywordId: Int,
    val name: String,
    val type: String, // "GENRE" or "STYLE"
)
