package com.arttrip.app.presentation.mypage.sub.settings.contract

import com.arttrip.app.BuildConfig

data class SettingsState(
    val appVersionName: String = BuildConfig.VERSION_NAME,
    val isDeleteAccountDialogVisible: Boolean = false,
)
