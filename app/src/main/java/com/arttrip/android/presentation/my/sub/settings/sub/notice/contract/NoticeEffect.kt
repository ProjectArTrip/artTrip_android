package com.arttrip.android.presentation.my.sub.settings.sub.notice.contract

sealed interface NoticeEffect {
    data object NavigateBack : NoticeEffect
}
