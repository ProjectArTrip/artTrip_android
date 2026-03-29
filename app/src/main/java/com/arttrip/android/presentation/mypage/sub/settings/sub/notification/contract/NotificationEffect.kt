package com.arttrip.android.presentation.mypage.sub.settings.sub.notification.contract

sealed interface NotificationEffect {
    data object NavigateBack : NotificationEffect
}
