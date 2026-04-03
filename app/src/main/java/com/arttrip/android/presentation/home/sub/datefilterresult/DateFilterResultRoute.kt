package com.arttrip.android.presentation.home.sub.datefilterresult

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultIntent
import java.time.LocalDate

@Composable
fun DateFilterResultRoute(
    innerPadding: PaddingValues,
    isDomestic: Boolean,
    location: String,
    startDate: LocalDate,
    endDate: LocalDate,
    viewModel: DateFilterResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(
            DateFilterResultIntent.Initialize(
                isDomestic = isDomestic,
                location = location,
                startDate = startDate,
                endDate = endDate,
            ),
        )
    }

    DateFilterResultScreen(
        innerPadding = innerPadding,
        state = state,
    )
}
