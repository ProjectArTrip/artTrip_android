package com.arttrip.app.presentation.splash.contract

data class SplashState(
    val isLoading: Boolean = true,
    val targetRoute: String? = null,
)
