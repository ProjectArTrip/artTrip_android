package com.arttrip.android.presentation.my.sub.settings.sub.notification.contract

import com.arttrip.android.BuildConfig

data class NotificationState(
    val appVersionName: String = BuildConfig.VERSION_NAME,
    val isDeleteAccountDialogVisible: Boolean = false,
)
