package com.arttrip.android.presentation.home.sub.datecountryresult

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.sub.datecountryresult.contract.DateCountryResultIntent
import java.time.LocalDate

@Composable
fun DateCountryResultRoute(
    innerPadding: PaddingValues,
    country: ForeignCountry,
    startDate: LocalDate,
    endDate: LocalDate,
    viewModel: DateCountryResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(country, startDate, endDate) {
        viewModel.onIntent(
            DateCountryResultIntent.Initialize(
                country = country,
                startDate = startDate,
                endDate = endDate,
            ),
        )
    }

    DateCountryResultScreen(
        innerPadding = innerPadding,
        state = state,
    )
}
