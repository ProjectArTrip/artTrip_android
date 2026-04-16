package com.arttrip.app.presentation.home.sub.genre.contract

import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry

data class GenreState(
    val country: ForeignCountry? = null,
    val selectedGenre: ExhibitionGenre? = null,
    val isFilterSheetVisible: Boolean = false,
    val selectedSortType: SortType = SortType.LATEST,
    val exhibitTotalCount: Int = 0,
)
