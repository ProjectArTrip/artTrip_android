package com.arttrip.app.presentation.mypage.sub.recentexhibitions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.presentation.mypage.sub.recentexhibitions.contract.RecentExhibitionsEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecentExhibitionsRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
    viewModel: RecentExhibitionsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                RecentExhibitionsEffect.NavigateBack -> onBack()
                is RecentExhibitionsEffect.NavigateToExhibitionDetail -> onNavigateExhibitionDetail(eff.exhibitId)
            }
        }
    }

    RecentExhibitionsScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
