package com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract

sealed interface NotificationEffect {
    data object NavigateBack : NotificationEffect
}
