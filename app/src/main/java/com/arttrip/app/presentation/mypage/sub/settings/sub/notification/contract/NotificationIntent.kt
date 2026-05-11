package com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract

sealed interface NotificationIntent {
    data object BackClicked : NotificationIntent

    data class ExhibitionInfoToggled(
        val enabled: Boolean,
    ) : NotificationIntent

    data class NoticePushToggled(
        val enabled: Boolean,
    ) : NotificationIntent
}
