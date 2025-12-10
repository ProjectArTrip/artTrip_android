package com.arttrip.android.presentation.intro.contract

sealed interface IntroEffect {
    data object NavigateToHome : IntroEffect

    data class ShowError(
        val message: String,
    ) : IntroEffect
}
