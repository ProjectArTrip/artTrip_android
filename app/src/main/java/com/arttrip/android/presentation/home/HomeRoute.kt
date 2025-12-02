package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        innerPadding = innerPadding,
        uiState = homeState,
        onIntent = viewModel::onIntent,
    )
}
