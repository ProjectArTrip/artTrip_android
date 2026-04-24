package com.arttrip.app.presentation.home.sub.schedule.contract

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
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

    data class LikeClicked(
        val id: Int,
    ) : ScheduleIntent
}
