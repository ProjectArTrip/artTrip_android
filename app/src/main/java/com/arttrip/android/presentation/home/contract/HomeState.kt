package com.arttrip.android.presentation.home.contract

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab
import com.arttrip.android.presentation.home.model.HomeSection
import java.time.DayOfWeek

private val weekOrderSundayFirst = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
)

private fun emptyCountryHomeData(): HomeSection =
    HomeSection(
        weeklyList = weekOrderSundayFirst.associateWith { emptyList() },
        genreList = ExhibitGenre.entries.associateWith { emptyList() }
    )

data class HomeState(
    val placeTabs: PlaceTab = PlaceTab.Foreign,

    val selectedCountry: ForeignCountry = ForeignCountry.Entire,

    val countryData: Map<ForeignCountry, HomeSection> =
        ForeignCountry.entries.associateWith { emptyCountryHomeData() },



    val interRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val interPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val interScheduledExhibitList: List<ExhibitModel> = emptyList(),
    val domesticRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val domesticPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val domesticScheduledExhibitList: List<ExhibitModel> = emptyList(),

    val foreignGenreChips: List<ExhibitGenre> = List(ForeignCountry.entries.size) { ExhibitGenre.ContemporaryArt },
    val domesticGenreChips: ExhibitGenre = ExhibitGenre.ContemporaryArt,
)