package com.arttrip.android.presentation.home.sub.dateresult

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DateResultRoute(
    innerPadding: PaddingValues,
    viewModel: DateResultViewModel = hiltViewModel(),
) {
    DateResultScreen(
        innerPadding = innerPadding,
    )
}
