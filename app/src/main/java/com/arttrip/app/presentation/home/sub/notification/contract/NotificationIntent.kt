package com.arttrip.app.presentation.home.sub.notification.contract

sealed interface NotificationIntent {
    object BackClicked : NotificationIntent
}
