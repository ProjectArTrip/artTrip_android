package com.arttrip.android.presentation.home.sub.datecountryresult.contract

import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

sealed interface DateCountryResultIntent {
    data class Initialize(
        val country: ForeignCountry,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : DateCountryResultIntent
}
