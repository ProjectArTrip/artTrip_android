package com.arttrip.android.presentation.home.contract

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry

sealed interface HomeEffect {
    object NavigateToNotification : HomeEffect

    object NavigateToDateFilter : HomeEffect

    object NavigateToSearch : HomeEffect

    data class NavigateToExhibitionDetail(
        val exhibitionId: Int,
    ) : HomeEffect

    data class NavigateToRegion(
        val region: DomesticRegion,
    ) : HomeEffect

    data class NavigateToForeignGenre(
        val country: ForeignCountry,
        val genre: ExhibitionGenre,
    ) : HomeEffect

    data class NavigateToDomesticGenre(
        val genre: ExhibitionGenre,
    ) : HomeEffect
}
