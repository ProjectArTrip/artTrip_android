package com.arttrip.app.presentation.home.sub.schedule.contract

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

data class ScheduleState(
    val selectedDate: LocalDate? = null,
    val country: ForeignCountry? = null,
)
