package com.arttrip.android.presentation.home.sub.genre.contract

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.SortType

data class GenreState(
    val selectedGenre: ExhibitionGenre? = null,
    val isFilterSheetVisible: Boolean = false,
    val selectedSortType: SortType = SortType.LATEST
)