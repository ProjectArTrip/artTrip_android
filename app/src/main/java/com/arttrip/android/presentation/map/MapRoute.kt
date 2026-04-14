package com.arttrip.android.presentation.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MapRoute(innerPadding: PaddingValues,
             viewModel: MapViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clusterExhibits = viewModel.clusterExhibits.collectAsLazyPagingItems()

    MapScreen(
        innerPadding = innerPadding,
        state = state,
        clusterExhibits = clusterExhibits,
        onIntent = viewModel::onIntent,
    )
}
