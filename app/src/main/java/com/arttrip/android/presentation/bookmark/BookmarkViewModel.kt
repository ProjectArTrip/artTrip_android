package com.arttrip.android.presentation.bookmark

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BookmarkViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(BookmarkState())
        val state: StateFlow<BookmarkState> = _state

        private val _effect = MutableSharedFlow<BookmarkEffect>()
        val effect: SharedFlow<BookmarkEffect> = _effect

        fun onIntent(intent: BookmarkIntent) {
            when (intent) {
                else -> {}
            }
        }
    }
