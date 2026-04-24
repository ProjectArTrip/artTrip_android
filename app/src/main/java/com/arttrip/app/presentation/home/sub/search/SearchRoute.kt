package com.arttrip.app.presentation.home.sub.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.home.sub.search.contract.SearchEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bookmarked by viewModel.bookmarked.collectAsStateWithLifecycle()

    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SearchEffect.NavigateBack -> onBack()
                is SearchEffect.NavigateToDetail -> onNavigateExhibitionDetail(effect.exhibitId)
            }
        }
    }

    SearchScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        exhibitionList = exhibitionList,
        bookmarked = bookmarked,
    )
}
