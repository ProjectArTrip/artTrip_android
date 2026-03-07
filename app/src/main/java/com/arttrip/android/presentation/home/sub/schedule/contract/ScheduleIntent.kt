package com.arttrip.android.presentation.home.sub.schedule.contract

import com.arttrip.android.core.model.enums.exhibition.SortType
import java.time.LocalDate

sealed interface ScheduleIntent {
    data class Initialize(
        val date: LocalDate,
    ) : ScheduleIntent

    object BackClicked : ScheduleIntent

    object NotificationIconClicked : ScheduleIntent

    object OpenFilterSheet : ScheduleIntent
    object CloseFilterSheet : ScheduleIntent

    data class SelectSortType(
        val type: SortType,
    ) : ScheduleIntent

    data class SelectDate(
        val date: LocalDate,
    ) : ScheduleIntent

    data class ExhibitionClicked(
        val exhibitionId: Int,
    ) : ScheduleIntent
}
