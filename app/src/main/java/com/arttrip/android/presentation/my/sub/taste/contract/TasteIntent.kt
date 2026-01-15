package com.arttrip.android.presentation.my.sub.taste.contract

sealed interface TasteIntent {
    data class ToggleGenre(
        val id: Int,
    ) : TasteIntent

    data class ToggleStyle(
        val id: Int,
    ) : TasteIntent

    data object Initialize : TasteIntent

    data object ClickNext : TasteIntent
}
