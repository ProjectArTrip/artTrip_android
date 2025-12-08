package com.arttrip.android.data.remote.model.auth

data class KeywordsResponseDto(
    val keywordId: Int,
    val name: String,
    val type: String, // "GENRE" or "STYLE"
)
