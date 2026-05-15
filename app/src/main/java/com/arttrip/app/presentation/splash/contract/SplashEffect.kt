package com.arttrip.app.presentation.splash.contract

sealed interface SplashEffect {
    data object ExitApp : SplashEffect
}
