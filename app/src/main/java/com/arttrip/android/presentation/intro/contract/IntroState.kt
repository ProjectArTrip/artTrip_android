package com.arttrip.android.presentation.intro.contract

import com.arttrip.android.domain.model.usertaste.TasteModel

data class IntroState(
    val genres: List<TasteModel> = emptyList(),
    val styles: List<TasteModel> = emptyList(),
    val selectedGenreIds: Set<Int> = emptySet(),
    val selectedStyleIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenreIds.isNotEmpty()
}
