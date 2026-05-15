package com.arttrip.app.presentation.splash.contract

data class SplashState(
    val isLoading: Boolean = true,
    val showMaintenanceDialog: Boolean = false,
    val targetRoute: String? = null,
    val startAt: String? = null,
    val endAt: String? = null,
)
