package com.arttrip.app.presentation.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.map.contract.MapEffect

@Composable
fun MapRoute(
    innerPadding: PaddingValues,
    onNavigateExhibitionDetail: (Int) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bookmarked by viewModel.bookmarked.collectAsStateWithLifecycle()
    val clusterExhibits = viewModel.clusterExhibits.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MapEffect.NavigateToExhibitionDetail -> onNavigateExhibitionDetail(effect.exhibitionId)
            }
        }
    }

    MapScreen(
        innerPadding = innerPadding,
        state = state,
        clusterExhibits = clusterExhibits,
        onIntent = viewModel::onIntent,
        bookmarked = bookmarked,
    )
}
