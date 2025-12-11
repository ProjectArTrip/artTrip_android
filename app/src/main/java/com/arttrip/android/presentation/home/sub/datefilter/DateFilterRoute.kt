package com.arttrip.android.presentation.home.sub.datefilter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DateFilterRoute(
    innerPadding: PaddingValues,
    viewModel: DateFilterViewModel = hiltViewModel(),
) {
    DateFilterScreen(
        innerPadding = innerPadding,
    )
}
