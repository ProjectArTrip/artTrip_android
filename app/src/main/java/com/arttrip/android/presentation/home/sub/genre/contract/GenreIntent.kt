package com.arttrip.android.presentation.home.sub.genre.contract

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.SortType

sealed interface GenreIntent {
    data class Initialize(
        val genre: ExhibitionGenre
    ) : GenreIntent

    object BackClicked : GenreIntent

    object NotificationIconClicked : GenreIntent

    object OpenFilterSheet : GenreIntent
    object CloseFilterSheet : GenreIntent

    data class SelectSortType(
        val type: SortType,
    ) : GenreIntent

    data class SelectGenre(
        val genre: ExhibitionGenre,
    ) : GenreIntent
}
