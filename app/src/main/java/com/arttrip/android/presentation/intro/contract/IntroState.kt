package com.arttrip.android.presentation.intro.contract

import com.arttrip.android.domain.model.auth.KeywordModel

data class IntroState(
    val genres: List<KeywordModel> = emptyList(),
    val styles: List<KeywordModel> = emptyList(),
    val selectedGenreIds: Set<Int> = emptySet(),
    val selectedStyleIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenreIds.isNotEmpty()
}
