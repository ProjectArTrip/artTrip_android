package com.arttrip.app.presentation.home.sub.curation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.home.sub.curation.contract.CurationEffect
import com.arttrip.app.presentation.home.sub.curation.contract.CurationIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurationRoute(
    innerPadding: PaddingValues,
    viewModel: CurationViewModel = hiltViewModel(),
    curationId: Long,
    onBack: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bookmarked by viewModel.bookmarked.collectAsStateWithLifecycle()
    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(curationId) {
        viewModel.onIntent(CurationIntent.Initialize(curationId = curationId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CurationEffect.NavigateBack -> onBack()
                CurationEffect.NavigateToNotification -> onNavigateNotification()
                is CurationEffect.NavigateToDetail -> onNavigateExhibitionDetail(effect.exhibitId)
            }
        }
    }

    CurationScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        exhibitionList = exhibitionList,
        bookmarked = bookmarked,
    )
}
