package com.arttrip.app.presentation.splash.contract

sealed interface SplashEffect {
    data object ExitApp : SplashEffect

    data class Navigate(
        val route: String,
    ) : SplashEffect
}
