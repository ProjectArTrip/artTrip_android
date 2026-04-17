package com.arttrip.app.presentation.intro.taste.contract

sealed interface TasteEffect {
    data object NavigateToHome : TasteEffect

    data class ShowError(
        val message: String,
    ) : TasteEffect
}
