package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect

@Composable
fun BookmarkRoute(
    innerPadding: PaddingValues,
    onNavigateExhibitionDetail: (Int) -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is BookmarkEffect.NavigateToDetail -> {
                    // TODO  onNavigateExhibitionDetail(effect.exhibitId)
                    onNavigateExhibitionDetail(1)
                }
                is BookmarkEffect.ShowToast -> {
                }
            }
        }
    }
    BookmarkScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
