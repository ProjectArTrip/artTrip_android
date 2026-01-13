package com.arttrip.android.presentation.my.sub.recentexhibitions.contract

sealed interface RecentExhibitionsIntent {
    data object BackClicked : RecentExhibitionsIntent
}
