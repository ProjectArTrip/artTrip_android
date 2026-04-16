package com.arttrip.app.presentation.mypage.sub.recentexhibitions.contract

sealed interface RecentExhibitionsEffect {
    data object NavigateBack : RecentExhibitionsEffect

    data class NavigateToExhibitionDetail(
        val exhibitId: Int,
    ) : RecentExhibitionsEffect
}
