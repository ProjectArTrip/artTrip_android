package com.arttrip.android.presentation.my.sub.recentexhibitions.contract

sealed interface RecentExhibitionsIntent {
    data object BackClicked : RecentExhibitionsIntent

    data class ExhibitionClicked(
        val exhibitId: Int,
    ) : RecentExhibitionsIntent
}
