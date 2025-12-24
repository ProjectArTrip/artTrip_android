package com.arttrip.android.presentation.home.contract

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab
import com.arttrip.android.presentation.home.model.HomeSection
import java.time.DayOfWeek
import java.time.LocalDate

data class HomeState(
    val placeTabs: PlaceTab = PlaceTab.Foreign,
    val selectedCountry: ForeignCountry = ForeignCountry.Entire,
    val foreignExhibitionData: Map<ForeignCountry, HomeSection> =
        ForeignCountry.entries.associateWith { emptyCountryHomeData() },
    val foreignSelectedDate: List<LocalDate> = List(ForeignCountry.entries.size) { LocalDate.now() },
    val foreignSelectedGenre: List<ExhibitionGenre> = List(ForeignCountry.entries.size) { ExhibitionGenre.ContemporaryArt },
    val domesticExhibitionData: HomeSection = emptyCountryHomeData(),
    val domesticSelectedDate: LocalDate = LocalDate.now(),
    val domesticSelectedGenre: ExhibitionGenre = ExhibitionGenre.ContemporaryArt,
)

private fun emptyCountryHomeData(): HomeSection =
    HomeSection(
        weeklyList = getThisWeekDates().associateWith { emptyList() },
        genreList = ExhibitionGenre.entries.associateWith { emptyList() },
    )

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

private fun getThisWeekDates(): List<LocalDate> {
    val today = LocalDate.now()

    val monday = today.with(DayOfWeek.MONDAY)

    return (0..6).map { offset ->
        monday.plusDays(offset.toLong())
    }
}
