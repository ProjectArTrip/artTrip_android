package com.arttrip.android.presentation.home.contract

import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab

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

    object LoadForeignRecommendExhibitList : HomeIntent

    object LoadForeignPersonalizedExhibitList : HomeIntent

    object LoadForeignScheduledExhibitList : HomeIntent

    object LoadForeignGenreExhibitList : HomeIntent

    object LoadDomesticRecommendExhibitList : HomeIntent

    object LoadDomesticPersonalizedExhibitList : HomeIntent

    object LoadDomesticScheduledExhibitList : HomeIntent

    object LoadDomesticGenreExhibitList : HomeIntent


    data class SelectForeignGenre(
        val genre: ExhibitGenre,
    ) : HomeIntent

    data class SelectDomesticGenre(
        val genre: ExhibitGenre,
    ) : HomeIntent
}
