package com.arttrip.app.presentation.home.sub.notification.contract

import com.arttrip.app.core.model.enums.notification.Action

sealed interface NotificationIntent {
    object BackClicked : NotificationIntent

    data class NotificationItemClicked(
        val userNoticeId: Int,
        val action: Action,
        val referenceId: Int,
    ) : NotificationIntent
}
