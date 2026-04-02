package com.arttrip.android.presentation.home.sub.schedule.contract

sealed interface ScheduleEffect {
    object NavigateBack : ScheduleEffect

    object NavigateToNotification : ScheduleEffect

    data class NavigateToExhibitionDetail(
        val exhibitionId: Int,
    ) : ScheduleEffect
}
