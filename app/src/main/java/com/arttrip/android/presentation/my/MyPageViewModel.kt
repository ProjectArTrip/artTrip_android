package com.arttrip.android.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.util.bookmark.BookmarkStore
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.presentation.my.contract.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
        val bookmarkStore: BookmarkStore,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MyPageState())
        val state: StateFlow<MyPageState> = _state

        fun logout() {
            sessionManager.logout()
        }

        init {
            viewModelScope.launch {
                state
                    .map { it.exhibitId }
                    .distinctUntilChanged()
                    .filterNotNull()
                    .collectLatest { id ->
                        bookmarkStore
                            .bookmarkedFlow(id)
                            .collectLatest { bookmarked ->
                                _state.update { it.copy(isBookmarked = bookmarked) }
                            }
                    }
            }
        }
    }
