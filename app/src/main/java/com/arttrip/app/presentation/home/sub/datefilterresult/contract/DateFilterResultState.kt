package com.arttrip.app.presentation.home.sub.datefilterresult.contract

import java.time.LocalDate

data class DateFilterResultState(
    val location: ExhibitionLocation? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val isDateFilterSheetVisible: Boolean = false,
    val dateFilterStartDate: LocalDate? = null,
    val dateFilterEndDate: LocalDate? = null,
    val dateFilterSelectedLocation: ExhibitionLocation? = null,
)
