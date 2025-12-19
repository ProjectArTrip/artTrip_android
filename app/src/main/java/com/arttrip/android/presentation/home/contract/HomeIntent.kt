package com.arttrip.android.presentation.home.contract

import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab
import java.time.LocalDate

sealed interface HomeIntent {
    data class SelectTab(
        val tab: PlaceTab,
    ) : HomeIntent

    data class SelectCountry(
        val country: ForeignCountry,
    ) : HomeIntent

    data class CountryClicked(
        val name: String,
    ) : HomeIntent

    object AlertIconClicked : HomeIntent

    object DateFilterIconClicked : HomeIntent

    object SearchIconClicked : HomeIntent


    data class LoadForeignRecommendExhibitList(
        val country: ForeignCountry
    ) : HomeIntent

    data class LoadForeignPersonalizedExhibitList(
        val country: ForeignCountry
    ) : HomeIntent

    data class LoadForeignScheduledExhibitList(
        val country: ForeignCountry,
        val date: LocalDate
    ) : HomeIntent

    data class LoadForeignGenreExhibitList(
        val country: ForeignCountry,
        val genre: ExhibitGenre
    ) : HomeIntent

    data class LoadDomesticRecommendExhibitList(
        val region: DomesticRegion
    ) : HomeIntent

    data class LoadDomesticPersonalizedExhibitList(
        val region: DomesticRegion
    ) : HomeIntent

    data class LoadDomesticScheduledExhibitList(
        val region: DomesticRegion,
        val date: LocalDate
    ) : HomeIntent

    data class LoadDomesticGenreExhibitList(
        val region: DomesticRegion,
        val genre: ExhibitGenre
    ) : HomeIntent


    data class SelectForeignGenre(
        val genre: ExhibitGenre,
    ) : HomeIntent

    data class SelectDomesticGenre(
        val genre: ExhibitGenre,
    ) : HomeIntent
}
