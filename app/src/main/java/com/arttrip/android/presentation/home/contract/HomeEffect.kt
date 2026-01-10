package com.arttrip.android.presentation.home.contract

import com.arttrip.android.core.model.enums.domestic.DomesticRegion

sealed interface HomeEffect {
    object NavigateToNotification : HomeEffect

    object NavigateToDateFilter : HomeEffect

    object NavigateToSearch : HomeEffect

    data class NavigateToExhibitionDetail(
        val exhibitionId: Int,
    ) : HomeEffect

    data class NavigateToRegion(
        val region: DomesticRegion,
    ) : HomeEffect
}
