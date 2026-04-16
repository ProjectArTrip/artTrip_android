package com.arttrip.app.presentation.home.sub.region.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion

data class RegionState(
    val selectedRegion: DomesticRegion = DomesticRegion.Seoul,
    val isDropdownExpanded: Boolean = false,
)
