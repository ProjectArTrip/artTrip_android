package com.arttrip.android.presentation.splash.contract

data class SplashState(
    val isLoading: Boolean = true,
    val targetRoute: String? = null,
)
