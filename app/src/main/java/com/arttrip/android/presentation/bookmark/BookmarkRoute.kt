package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect

@Composable
fun BookmarkRoute(
    innerPadding: PaddingValues,
    onNavigateExhibitionDetail: (Int) -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bookmarks = viewModel.bookmarksFlow.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            if (!viewModel.isInitialLoad) {
                bookmarks.refresh()
                listState.scrollToItem(0)
            }
            viewModel.onResumed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is BookmarkEffect.NavigateToDetail -> {
                    onNavigateExhibitionDetail(effect.exhibitId)
                }
                is BookmarkEffect.ShowToast -> {
                }
            }
        }
    }
    BookmarkScreen(
        innerPadding = innerPadding,
        state = state,
        bookmarks = bookmarks,
        onIntent = viewModel::onIntent,
        onSetBookmarkFromRemote = viewModel::setBookmarkFromRemote,
        bookmarkedFlow = viewModel::bookmarkedFlow,
    )
}
