package com.arttrip.app.presentation.stamp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StampRoute(
    innerPadding: PaddingValues,
    viewModel: StampViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StampScreen(
        innerPadding = innerPadding,
        state = state,
    )
}
