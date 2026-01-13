package com.arttrip.android.presentation.my.sub.recentexhibitions.contract

sealed interface RecentExhibitionsEffect {
    data object NavigateBack : RecentExhibitionsEffect
}
