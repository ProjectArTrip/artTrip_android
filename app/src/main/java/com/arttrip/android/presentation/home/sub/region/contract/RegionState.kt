package com.arttrip.android.presentation.home.sub.region.contract

import com.arttrip.android.core.model.enums.domestic.DomesticRegion

data class RegionState(
    val selectedRegion: DomesticRegion = DomesticRegion.Seoul,
    val isDropdownExpanded: Boolean = false,
)
