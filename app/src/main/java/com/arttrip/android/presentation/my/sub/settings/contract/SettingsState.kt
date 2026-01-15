package com.arttrip.android.presentation.my.sub.settings.contract

data class SettingsState(
    val appVersion: String = "1.0.0",
    val isDeleteAccountDialogVisible: Boolean = false,
)
