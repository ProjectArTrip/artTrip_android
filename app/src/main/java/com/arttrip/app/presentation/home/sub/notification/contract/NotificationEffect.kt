package com.arttrip.app.presentation.home.sub.notification.contract

sealed interface NotificationEffect {
    object NavigateBack : NotificationEffect
}
