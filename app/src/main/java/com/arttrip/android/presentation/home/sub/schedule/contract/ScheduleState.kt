package com.arttrip.android.presentation.home.sub.schedule.contract

import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

data class ScheduleState(
    val selectedDate: LocalDate? = null,
    val isFilterSheetVisible: Boolean = false,
    val selectedSortType: SortType = SortType.LATEST,
    val country: ForeignCountry? = null,
)
