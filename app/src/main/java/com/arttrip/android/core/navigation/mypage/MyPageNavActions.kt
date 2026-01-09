package com.arttrip.android.core.navigation.mypage

import com.arttrip.android.presentation.reviewwrite.model.ReviewWritePrefill

/**
 * MyPage 화면에서 쓸 네비 액션 묶음
 * - 마이페이지 하위로 가는 것(tab)
 * - 풀팝업(공용)로 올리는 것(main)
 */
data class MyPageNavActions(
    val toEditProfile: () -> Unit,
    val toRecentExhibitions: () -> Unit,
    val toMyReviews: () -> Unit,
    val toTasteAnalysis: () -> Unit,
    val toSettings: () -> Unit,
    // 공용 풀팝업(리뷰 작성/수정)
    val toReviewWrite: (exhibitId: Int, prefill: ReviewWritePrefill) -> Unit,
)
