package com.arttrip.app.presentation.intro.contract

sealed interface IntroEffect {
    data object NavigateToHome : IntroEffect

    data class ShowError(
        val message: String,
    ) : IntroEffect
}
