package com.arttrip.app.presentation.bookmark.model

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.foreign.ForeignCountry

data class BookmarkLocationFilter(
    val foreignCountries: Set<ForeignCountry> = setOf(ForeignCountry.Entire),
    val domesticRegions: Set<DomesticRegion> = setOf(DomesticRegion.Entire),
)
