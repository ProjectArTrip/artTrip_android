package com.arttrip.android.presentation.my.sub.settings.contract

sealed interface SettingsIntent {
    data object BackClicked : SettingsIntent

    data object NotificationSettingsClick : SettingsIntent

    data object NoticeClick : SettingsIntent

    data object PrivacyPolicyClick : SettingsIntent

    data object TermsOfServiceClick : SettingsIntent

    data object DeleteAccountClick : SettingsIntent

    data object DeleteAccountConfirmClick : SettingsIntent

    data object DeleteAccountDialogDismissed : SettingsIntent
}
