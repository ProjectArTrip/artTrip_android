package com.arttrip.android.domain.model.auth

import com.arttrip.android.data.remote.model.auth.KeywordType

data class KeywordModel(
    val id: Int,
    val name: String,
    val type: KeywordType,
)
