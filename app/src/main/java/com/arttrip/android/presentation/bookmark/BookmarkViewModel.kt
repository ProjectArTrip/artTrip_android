package com.arttrip.android.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.util.bookmark.BookmarkStore
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkState
import com.arttrip.android.presentation.bookmark.model.BookmarkLocationFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
                is BookmarkIntent.ClickItem -> {
                    viewModelScope.launch { _effect.emit(BookmarkEffect.NavigateToDetail(intent.exhibitId)) }
                }
                is BookmarkIntent.ToggleBookmark -> {
                    viewModelScope.launch {
                        // TODO: 서버 연동 시 API 호출 후 성공 시 store 업데이트
                        //  bookmarkStore.toggle(intent.exhibitId)
                    }
                }

                BookmarkIntent.FilterSheetOpened -> {
                    _state.update { s ->
                        s.copy(
                            isFilterSheetVisible = true,
                            editingLocationFilter = s.appliedLocationFilter,
                        )
                    }
                }

                BookmarkIntent.FilterSheetDismissed -> {
                    _state.update { s ->
                        s.copy(
                            isFilterSheetVisible = false,
                            editingLocationFilter = s.appliedLocationFilter,
                        )
                    }
                }

                is BookmarkIntent.ToggleForeignCountry ->
                    _state.update { s ->
                        val next =
                            toggleWithAllAllowEmpty(
                                current = s.editingLocationFilter.foreignCountries,
                                target = intent.country,
                                allValue = ForeignCountry.Entire,
                            )
                        s.copy(
                            editingLocationFilter = s.editingLocationFilter.copy(foreignCountries = next),
                        )
                    }

                is BookmarkIntent.ToggleDomesticRegion ->
                    _state.update { s ->
                        val next =
                            toggleWithAllAllowEmpty(
                                current = s.editingLocationFilter.domesticRegions,
                                target = intent.region,
                                allValue = DomesticRegion.Entire,
                            )
                        s.copy(
                            editingLocationFilter = s.editingLocationFilter.copy(domesticRegions = next),
                        )
                    }

                BookmarkIntent.ResetFilter -> {
                    _state.update { s ->
                        s.copy(
                            editingLocationFilter =
                                BookmarkLocationFilter(
                                    foreignCountries = emptySet(),
                                    domesticRegions = emptySet(),
                                ),
                        )
                    }
                }

                BookmarkIntent.ClickSearch -> {
                    val cur = _state.value
                    if (!cur.isSearchEnabled) return
                    // TODO: repository.fetchBookmarks(filter) 연결
                    _state.update { s ->
                        s.copy(
                            isFilterSheetVisible = false,
                            appliedLocationFilter = s.editingLocationFilter,
                        )
                    }
                }
            }
        }

        private fun observeBookmarkListFlags() {
            viewModelScope.launch {
                bookmarkStore
                    .bookmarkedByIdsFlow(
                        state
                            .map { state ->
                                state.bookmarkList.map { item -> item.id }
                            },
                    ).collectLatest { map ->
                        _state.update { it.copy(bookmarkedMap = map) }
                    }
            }
        }

        private fun <T> toggleWithAllAllowEmpty(
            current: Set<T>,
            target: T,
            allValue: T,
        ): Set<T> {
            if (target == allValue) {
                return if (allValue in current) emptySet() else setOf(allValue)
            }

            val base = current - allValue
            val next = if (target in base) base - target else base + target

            return next
        }
    }
