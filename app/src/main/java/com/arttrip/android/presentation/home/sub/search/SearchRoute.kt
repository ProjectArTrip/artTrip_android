package com.arttrip.android.presentation.home.sub.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SearchEffect.NavigateBack -> onBack()
            }
        }
    }

    SearchScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
