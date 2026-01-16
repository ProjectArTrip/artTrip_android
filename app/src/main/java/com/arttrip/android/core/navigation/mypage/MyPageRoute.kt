package com.arttrip.android.core.navigation.mypage

/**
 * MyPage 내부 라우트 경로들
 * (MainRoute랑 충돌 방지 위해 prefix로 mypage/ 사용)
 */
object MyPageRoute {
    const val ROOT = "mypage/root"
    const val EDIT_PROFILE = "mypage/edit_profile"
    const val RECENT_EXHIBITIONS = "mypage/recent_exhibitions"
    const val MY_REVIEWS = "mypage/my_reviews"
    const val SETTINGS = "mypage/settings"
    const val NOTIFICATION = "mypage/settings/notification"
    const val NOTICE = "mypage/settings/notice"
}
