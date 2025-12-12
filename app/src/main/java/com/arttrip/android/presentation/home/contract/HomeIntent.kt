package com.arttrip.android.presentation.home.contract

sealed interface HomeIntent {
    object LoadCountries : HomeIntent

    object Retry : HomeIntent

    data class CountryClicked(
        val name: String,
    ) : HomeIntent

    object DateFilterIconClicked : HomeIntent

    object LoadInterRecommendExhibitList: HomeIntent
    object LoadInterPersonalizedExhibitList: HomeIntent

    object LoadInterScheduledExhibitList: HomeIntent

    object LoadDomesticRecommendExhibitList: HomeIntent
    object LoadDomesticPersonalizedExhibitList: HomeIntent

    object LoadDomesticScheduledExhibitList: HomeIntent
}
