package com.arttrip.android.presentation.home.sub.schedule.contract

import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

sealed interface ScheduleIntent {
    data class Initialize(
        val date: LocalDate,
        val country: ForeignCountry?,
    ) : ScheduleIntent

    object BackClicked : ScheduleIntent

    object NotificationIconClicked : ScheduleIntent

    data class SelectDate(
        val date: LocalDate,
    ) : ScheduleIntent

    data class ExhibitionClicked(
        val exhibitionId: Int,
    ) : ScheduleIntent
}
