package com.arttrip.android.domain.model.userkeyword

import com.arttrip.android.domain.model.userkeyword.KeywordModel

data class KeywordGroups(
    val genres: List<KeywordModel>,
    val styles: List<KeywordModel>,
)
