package com.arttrip.android.presentation.my.sub.settings.sub.notice.contract

import com.arttrip.android.BuildConfig

data class NoticeState(
    val appVersionName: String = BuildConfig.VERSION_NAME,
    val isDeleteAccountDialogVisible: Boolean = false,
)
