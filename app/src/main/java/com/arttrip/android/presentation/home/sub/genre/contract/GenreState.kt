package com.arttrip.android.presentation.home.sub.genre.contract

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry

data class GenreState(
    val country: ForeignCountry? = null,
    val selectedGenre: ExhibitionGenre? = null,
    val isFilterSheetVisible: Boolean = false,
    val selectedSortType: SortType = SortType.LATEST,
    val exhibitTotalCount: Int = 0,
)
