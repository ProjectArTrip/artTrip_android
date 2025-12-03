package com.arttrip.android.presentation.intro.contract

data class IntroState(
    val selectedGenreIds: List<Int> = emptyList(),
    val selectedStyleIds: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenreIds.isNotEmpty() && selectedStyleIds.isNotEmpty()
}
