package com.arttrip.app.presentation.home.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

sealed interface HomeEffect {
    object NavigateToNotification : HomeEffect

    data class NavigateToDateFilterResult(
        val isDomestic: Boolean,
        val location: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : HomeEffect

    object NavigateToSearch : HomeEffect

    data class NavigateToExhibitionDetail(
        val exhibitionId: Int,
    ) : HomeEffect

    data class NavigateToRegion(
        val region: DomesticRegion,
    ) : HomeEffect

    data class NavigateToForeignSchedule(
        val country: ForeignCountry,
        val date: LocalDate,
    ) : HomeEffect

    data class NavigateToDomesticSchedule(
        val date: LocalDate,
    ) : HomeEffect

    data class NavigateToForeignGenre(
        val country: ForeignCountry,
        val genre: ExhibitionGenre,
    ) : HomeEffect

    data class NavigateToDomesticGenre(
        val genre: ExhibitionGenre,
    ) : HomeEffect
}
