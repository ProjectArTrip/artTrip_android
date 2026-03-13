package com.arttrip.android.presentation.home.sub.search

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(exhibitionList.loadState, exhibitionList.itemCount) {
        Log.d(
            "SearchPaging",
            "refresh=${exhibitionList.loadState.refresh}, append=${exhibitionList.loadState.append}, count=${exhibitionList.itemCount}"
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SearchEffect.NavigateBack -> onBack()
                is SearchEffect.NavigateToDetail -> TODO()
            }
        }
    }

    SearchScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        exhibitionList = exhibitionList
    )
}
