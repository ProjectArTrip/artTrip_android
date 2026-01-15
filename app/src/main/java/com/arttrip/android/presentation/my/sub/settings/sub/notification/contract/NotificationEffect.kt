package com.arttrip.android.presentation.my.sub.settings.sub.notification.contract

sealed interface NotificationEffect {
    data object NavigateBack : NotificationEffect
}
