package com.arttrip.android.presentation.mypage.sub.taste.contract

import com.arttrip.android.domain.model.usertaste.Taste

data class TasteState(
    val genres: List<Taste> = emptyList(),
    val styles: List<Taste> = emptyList(),
    val selectedGenresNames: Set<String> = emptySet(),
    val selectedStyleNames: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedGenresNames.isNotEmpty()
}
