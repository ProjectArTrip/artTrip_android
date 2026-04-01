package com.arttrip.android.presentation.mypage.sub.taste.contract

sealed interface TasteIntent {
    data object BackClicked : TasteIntent

    data class ToggleGenre(
        val name: String,
    ) : TasteIntent

    data class ToggleStyle(
        val name: String,
    ) : TasteIntent

    data object Initialize : TasteIntent

    data object SaveClicked : TasteIntent
}
