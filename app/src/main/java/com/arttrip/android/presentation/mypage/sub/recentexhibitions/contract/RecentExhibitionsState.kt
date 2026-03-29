package com.arttrip.android.presentation.mypage.sub.recentexhibitions.contract

import com.arttrip.android.domain.model.exhibition.RecentExhibition

data class RecentExhibitionsState(
    val isLoading: Boolean = false,
    val exhibitions: List<RecentExhibition> = emptyList(),
) {
    val isEmpty: Boolean
        get() = exhibitions.isEmpty()
}
