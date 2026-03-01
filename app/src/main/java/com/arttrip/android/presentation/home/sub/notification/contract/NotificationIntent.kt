package com.arttrip.android.presentation.home.sub.notification.contract

sealed interface NotificationIntent {
    object BackClicked : NotificationIntent
}
