package com.arttrip.app.presentation.mypage.sub.taste.contract

import com.arttrip.app.domain.model.usertaste.Taste

data class TasteState(
    val nickname: String = "사용자",
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
