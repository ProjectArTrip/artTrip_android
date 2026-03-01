package com.arttrip.android.presentation.intro.contract

sealed interface IntroIntent {
    data class ToggleGenre(
        val name: String,
    ) : IntroIntent

    data class ToggleStyle(
        val name: String,
    ) : IntroIntent

    data object Initialize : IntroIntent

    data object ClickNext : IntroIntent
}
