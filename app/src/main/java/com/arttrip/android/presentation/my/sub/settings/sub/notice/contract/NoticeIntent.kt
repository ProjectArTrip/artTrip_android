package com.arttrip.android.presentation.my.sub.settings.sub.notice.contract

sealed interface NoticeIntent {
    data object BackClicked : NoticeIntent
}
