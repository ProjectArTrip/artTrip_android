package com.arttrip.android.presentation.home.contract

import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab
import com.arttrip.android.presentation.home.model.HomeSection
import java.time.DayOfWeek

private val weekOrderSundayFirst =
    listOf(
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
        genreList = ExhibitGenre.entries.associateWith { emptyList() },
    )

data class HomeState(
    val placeTabs: PlaceTab = PlaceTab.Foreign,
    val selectedCountry: ForeignCountry = ForeignCountry.Entire,
    val countryData: Map<ForeignCountry, HomeSection> =
        ForeignCountry.entries.associateWith { emptyCountryHomeData() },
    val interRecommendExhibitList: List<ExhibitionModel> = emptyList(),
    val interPersonalizedExhibitList: List<ExhibitionModel> = emptyList(),
    val interScheduledExhibitList: List<ExhibitionModel> = emptyList(),
    val domesticRecommendExhibitList: List<ExhibitionModel> = emptyList(),
    val domesticPersonalizedExhibitList: List<ExhibitionModel> = emptyList(),
    val domesticScheduledExhibitList: List<ExhibitionModel> = emptyList(),
    val foreignGenreChips: List<ExhibitGenre> = List(ForeignCountry.entries.size) { ExhibitGenre.ContemporaryArt },
    val domesticGenreChips: ExhibitGenre = ExhibitGenre.ContemporaryArt,
)
