package com.arttrip.app.presentation.splash.contract

sealed interface SplashIntent {
    data object ExitApp : SplashIntent
}
