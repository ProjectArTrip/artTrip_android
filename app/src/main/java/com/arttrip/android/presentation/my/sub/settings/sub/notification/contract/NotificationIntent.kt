package com.arttrip.android.presentation.my.sub.settings.sub.notification.contract

sealed interface NotificationIntent {
    data object BackClicked : NotificationIntent
}
