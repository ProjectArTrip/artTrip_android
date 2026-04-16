package com.arttrip.app.presentation.home.sub.region.contract

sealed interface RegionEffect {
    object NavigateBack : RegionEffect

    data class NavigateToDetail(
        val exhibitId: Int,
    ) : RegionEffect
}
