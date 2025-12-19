package com.arttrip.android.data.remote.model.auth

data class KeywordsResDto(
    val keywordId: Int,
    val name: String,
    val type: String, // "GENRE" or "STYLE"
)
