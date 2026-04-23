package com.arttrip.app.presentation.home.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.presentation.home.PlaceTab
import java.time.LocalDate

sealed interface HomeIntent {
    data class SelectTab(
        val tab: PlaceTab,
    ) : HomeIntent

    data class CountryClicked(
        val country: ForeignCountry,
    ) : HomeIntent

    object NotificationIconClicked : HomeIntent

    object DateFilterIconClicked : HomeIntent

    object DateFilterSheetDismissed : HomeIntent

    object DateFilterApplyClicked : HomeIntent

    object DateFilterResetClicked : HomeIntent

    object DateFilterDateSectionOpened : HomeIntent

    data class DateFilterDayClicked(
        val date: LocalDate,
    ) : HomeIntent

    data class DateFilterCountrySelected(
        val country: ForeignCountry,
    ) : HomeIntent

    data class DateFilterRegionSelected(
        val region: DomesticRegion,
    ) : HomeIntent

    object SearchIconClicked : HomeIntent

    data class LoadForeignRecommendExhibitList(
        val country: ForeignCountry,
    ) : HomeIntent

    data class LoadForeignPersonalizedExhibitList(
        val country: ForeignCountry,
    ) : HomeIntent

    data class LoadForeignScheduledExhibitList(
        val country: ForeignCountry,
        val date: LocalDate,
    ) : HomeIntent

    data class LoadForeignGenreExhibitList(
        val country: ForeignCountry,
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class LoadDomesticRecommendExhibitList(
        val region: DomesticRegion,
    ) : HomeIntent

    data class LoadDomesticPersonalizedExhibitList(
        val region: DomesticRegion,
    ) : HomeIntent

    data class LoadDomesticScheduledExhibitList(
        val region: DomesticRegion,
        val date: LocalDate,
    ) : HomeIntent

    data class LoadDomesticGenreExhibitList(
        val region: DomesticRegion,
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class SelectForeignDate(
        val date: LocalDate,
    ) : HomeIntent

    data class SelectDomesticDate(
        val date: LocalDate,
    ) : HomeIntent

    data class SelectForeignGenre(
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class SelectDomesticGenre(
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class ExhibitionClicked(
        val id: Int,
    ) : HomeIntent

    data class RegionClicked(
        val region: DomesticRegion,
    ) : HomeIntent

    data class ForeignMoreScheduleIconClicked(
        val country: ForeignCountry,
        val date: LocalDate,
    ) : HomeIntent

    data class DomesticMoreScheduleIconClicked(
        val date: LocalDate,
    ) : HomeIntent

    data class ForeignMoreGenreIconClicked(
        val country: ForeignCountry,
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class DomesticMoreGenreIconClicked(
        val genre: ExhibitionGenre,
    ) : HomeIntent

    data class ToggleBookmark(
        val id: Int,
    ) : HomeIntent
}
