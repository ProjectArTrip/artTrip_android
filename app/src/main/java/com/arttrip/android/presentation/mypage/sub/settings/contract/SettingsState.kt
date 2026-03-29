package com.arttrip.android.presentation.mypage.sub.settings.contract

import com.arttrip.android.BuildConfig

data class SettingsState(
    val appVersionName: String = BuildConfig.VERSION_NAME,
    val isDeleteAccountDialogVisible: Boolean = false,
)
