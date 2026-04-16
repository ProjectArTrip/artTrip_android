package com.arttrip.app.presentation.mypage.sub.settings.sub.notice.contract

sealed interface NoticeEffect {
    data object NavigateBack : NoticeEffect
}
