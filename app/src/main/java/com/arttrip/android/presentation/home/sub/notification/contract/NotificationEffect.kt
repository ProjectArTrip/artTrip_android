package com.arttrip.android.presentation.home.sub.notification.contract

sealed interface NotificationEffect {
    object NavigateBack : NotificationEffect
}
