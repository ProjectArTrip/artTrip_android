package com.arttrip.android.presentation.home.sub.datefilterresult.contract

import java.time.LocalDate

sealed interface DateFilterResultIntent {
    data class Initialize(
        val isDomestic: Boolean,
        val location: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : DateFilterResultIntent
}
