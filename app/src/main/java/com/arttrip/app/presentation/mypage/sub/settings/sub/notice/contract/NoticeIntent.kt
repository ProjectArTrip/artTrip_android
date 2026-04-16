package com.arttrip.app.presentation.mypage.sub.settings.sub.notice.contract

sealed interface NoticeIntent {
    data object BackClicked : NoticeIntent
}
