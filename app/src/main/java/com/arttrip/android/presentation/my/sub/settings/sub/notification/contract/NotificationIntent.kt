package com.arttrip.android.presentation.my.sub.settings.sub.notification.contract

sealed interface NotificationIntent {
    data object BackClicked : NotificationIntent

    data class ExhibitionInfoToggled(
        val enabled: Boolean,
    ) : NotificationIntent

    data class SavedExhibitionOpenToggled(
        val enabled: Boolean,
    ) : NotificationIntent

    data class StampIssuedToggled(
        val enabled: Boolean,
    ) : NotificationIntent

    data class MarketingPushToggled(
        val enabled: Boolean,
    ) : NotificationIntent
}
