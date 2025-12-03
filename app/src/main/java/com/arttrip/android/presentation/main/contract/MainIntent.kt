package com.arttrip.android.presentation.main.contract

sealed interface MainIntent {
    data object OnLoginSuccess : MainIntent

    data object OnLogout : MainIntent
}
