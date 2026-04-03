package com.arttrip.android.presentation.home.contract

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab
import com.arttrip.android.presentation.home.model.HomeSection
import com.arttrip.android.presentation.home.model.SectionLoadState
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
    val isDateFilterSheetVisible: Boolean = false,
    val dateFilterStartDate: LocalDate? = null,
    val dateFilterEndDate: LocalDate? = null,
    val dateFilterSelectedCountry: ForeignCountry? = null,
    val dateFilterSelectedRegion: DomesticRegion? = null,
)

private fun emptyCountryHomeData(): HomeSection =
    HomeSection(
        scheduleList = getThisWeekDates().associateWith { SectionLoadState.Idle },
        genreList = ExhibitionGenre.entries.associateWith { SectionLoadState.Idle },
    )

private fun getThisWeekDates(): List<LocalDate> {
    val today = LocalDate.now()

    val monday = today.with(DayOfWeek.MONDAY)

    return (0..6).map { offset ->
        monday.plusDays(offset.toLong())
    }
}
