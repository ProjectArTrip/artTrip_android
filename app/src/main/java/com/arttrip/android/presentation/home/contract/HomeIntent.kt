package com.arttrip.android.presentation.home.contract

import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab

sealed interface HomeIntent {
    data class SelectTab(val tab: PlaceTab) : HomeIntent
    data class SelectCountry(val country: ForeignCountry) : HomeIntent
    object LoadCountries : HomeIntent

    object Retry : HomeIntent

    data class CountryClicked(
        val name: String,
    ) : HomeIntent

    object AlertIconClicked : HomeIntent

    object DateFilterIconClicked : HomeIntent

    object SearchIconClicked : HomeIntent

    object LoadInterRecommendExhibitList: HomeIntent
    object LoadInterPersonalizedExhibitList: HomeIntent

    object LoadInterScheduledExhibitList: HomeIntent

    object LoadDomesticRecommendExhibitList: HomeIntent
    object LoadDomesticPersonalizedExhibitList: HomeIntent

    object LoadDomesticScheduledExhibitList: HomeIntent
}
