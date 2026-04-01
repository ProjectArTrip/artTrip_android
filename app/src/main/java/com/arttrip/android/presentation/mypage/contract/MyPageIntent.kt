package com.arttrip.android.presentation.mypage.contract

sealed interface MyPageIntent {
    data object ClickProfileMore : MyPageIntent

    data object ClickRecentExhibitions : MyPageIntent

    data object ClickMyReviews : MyPageIntent

    data object ClickTasteAnalysis : MyPageIntent

    data object ClickSettings : MyPageIntent

    data object ClickLogout : MyPageIntent
}
