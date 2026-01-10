package com.arttrip.android.presentation.intro.contract

import com.arttrip.android.domain.model.usertaste.Taste

data class IntroState(
    val genres: List<Taste> = emptyList(),
    val styles: List<Taste> = emptyList(),
    val selectedGenreIds: Set<Int> = emptySet(),
    val selectedStyleIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenreIds.isNotEmpty()
}
