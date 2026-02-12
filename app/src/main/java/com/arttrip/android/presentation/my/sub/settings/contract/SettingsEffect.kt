package com.arttrip.android.presentation.my.sub.settings.contract

sealed interface SettingsEffect {
    data object NavigateBack : SettingsEffect

    data object NavigateToNotice : SettingsEffect

    data object NavigateToNotification : SettingsEffect

    data class OpenWeb(
        val url: String,
    ) : SettingsEffect
}
