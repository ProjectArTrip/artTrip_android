package com.arttrip.android.presentation.mypage.sub.settings.sub.notification.contract

data class NotificationState(
    val exhibitionInfoEnabled: Boolean = true,
    val savedExhibitionOpenEnabled: Boolean = true,
    val stampIssuedEnabled: Boolean = true,
    val marketingPushEnabled: Boolean = true,
)
