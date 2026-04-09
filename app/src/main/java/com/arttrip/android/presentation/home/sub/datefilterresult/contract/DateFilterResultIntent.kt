package com.arttrip.android.presentation.home.sub.datefilterresult.contract

import java.time.LocalDate

sealed interface DateFilterResultIntent {
    data object BackClicked : DateFilterResultIntent

    data class Initialize(
        val isDomestic: Boolean,
        val location: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : DateFilterResultIntent

    data object DateFilterIconClicked : DateFilterResultIntent

    data object DateFilterSheetDismissed : DateFilterResultIntent

    data object DateFilterApplyClicked : DateFilterResultIntent

    data object DateFilterResetClicked : DateFilterResultIntent

    data object DateFilterDateSectionOpened : DateFilterResultIntent

    data class DateFilterDayClicked(
        val date: LocalDate,
    ) : DateFilterResultIntent

    data class DateFilterLocationSelected(
        val location: ExhibitionLocation,
    ) : DateFilterResultIntent
}
