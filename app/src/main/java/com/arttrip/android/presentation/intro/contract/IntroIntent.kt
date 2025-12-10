package com.arttrip.android.presentation.intro.contract

sealed interface IntroIntent {
    data class ToggleGenre(
        val id: Int,
    ) : IntroIntent

    data class ToggleStyle(
        val id: Int,
    ) : IntroIntent

    data object Initialize : IntroIntent

    data object ClickNext : IntroIntent
}
