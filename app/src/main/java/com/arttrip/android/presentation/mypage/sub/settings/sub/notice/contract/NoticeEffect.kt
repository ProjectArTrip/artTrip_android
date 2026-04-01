package com.arttrip.android.presentation.mypage.sub.settings.sub.notice.contract

sealed interface NoticeEffect {
    data object NavigateBack : NoticeEffect
}
