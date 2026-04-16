package com.arttrip.app.presentation.mypage.sub.recentexhibitions.contract

sealed interface RecentExhibitionsIntent {
    data object BackClicked : RecentExhibitionsIntent

    data object Initialize : RecentExhibitionsIntent

    data class ExhibitionClicked(
        val exhibitId: Int,
    ) : RecentExhibitionsIntent
}
