package com.arttrip.app.presentation.intro.taste.contract

sealed interface TasteIntent {
    data class ToggleGenre(
        val name: String,
    ) : TasteIntent

    data class ToggleStyle(
        val name: String,
    ) : TasteIntent

    data object Initialize : TasteIntent

    data object ClickNext : TasteIntent
}
