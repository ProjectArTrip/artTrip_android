package com.arttrip.android.presentation.home.sub.region.contract

import com.arttrip.android.presentation.home.contract.HomeIntent

sealed interface RegionIntent {
    object BackIconClicked : RegionIntent

    object DownIconClicked : RegionIntent

    data class ExhibitionClicked(
        val id: Int,
    ) : RegionIntent

    data class LikeClicked(
        val id: Int,
    ) : RegionIntent
}