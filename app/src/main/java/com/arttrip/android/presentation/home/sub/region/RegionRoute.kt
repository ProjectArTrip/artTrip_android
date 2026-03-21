package com.arttrip.android.presentation.home.sub.region

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.presentation.home.sub.region.contract.RegionEffect
import com.arttrip.android.presentation.home.sub.region.contract.RegionIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegionRoute(
    innerPadding: PaddingValues,
    viewModel: RegionViewModel = hiltViewModel(),
    region: DomesticRegion,
    onBack: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(region) {
        viewModel.onIntent(RegionIntent.ScreenEntered(region))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RegionEffect.NavigateBack -> onBack()
                is RegionEffect.NavigateToDetail -> onNavigateExhibitionDetail(effect.exhibitId)
            }
        }
    }

    RegionScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        exhibitionList = exhibitionList,
    )
}