package com.arttrip.app.presentation.home.sub.genre.contract

import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry

sealed interface GenreIntent {
    data class Initialize(
        val country: ForeignCountry?,
        val genre: ExhibitionGenre,
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

    data class ExhibitionClicked(
        val id: Int,
    ) : GenreIntent

    data class LikeClicked(
        val id: Int,
    ) : GenreIntent
}
