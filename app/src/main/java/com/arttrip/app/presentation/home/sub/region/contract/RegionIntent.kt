package com.arttrip.app.presentation.home.sub.region.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion

sealed interface RegionIntent {
    data class ScreenEntered(
        val region: DomesticRegion,
    ) : RegionIntent

    object BackIconClicked : RegionIntent

    object DropdownClicked : RegionIntent

    object DropdownDismissed : RegionIntent

    data class RegionSelected(
        val region: DomesticRegion,
    ) : RegionIntent

    data class ExhibitionClicked(
        val id: Int,
    ) : RegionIntent

    data class LikeClicked(
        val id: Int,
    ) : RegionIntent
}
