package com.arttrip.android.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.util.bookmark.BookmarkStore
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkViewModel
    @Inject
    constructor(
        private val bookmarkStore: BookmarkStore,
    ) : ViewModel() {
        private val _state = MutableStateFlow(BookmarkState())
        val state: StateFlow<BookmarkState> = _state

        private val _effect = MutableSharedFlow<BookmarkEffect>()
        val effect: SharedFlow<BookmarkEffect> = _effect

        init {
            observeBookmarkListFlags()
        }

        fun onIntent(intent: BookmarkIntent) {
            when (intent) {
                // TODO bookstore binding 추가
                is BookmarkIntent.ChangeSort -> {
                    _state.update { it.copy(sort = intent.sort) }
                }
            }
        }

        private fun observeBookmarkListFlags() {
            viewModelScope.launch {
                bookmarkStore
                    .bookmarkedByIdsFlow(
                        state
                            .map { state ->
                                state.bookmarkList.map { item -> item.exhibitId }
                            },
                    ).collectLatest { map ->
                        _state.update { it.copy(bookmarkedMap = map) }
                    }
            }
        }
    }
