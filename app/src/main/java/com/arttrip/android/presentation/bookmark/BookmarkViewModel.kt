package com.arttrip.android.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.util.bookmark.BookmarkStore
import com.arttrip.android.domain.model.favorite.Bookmark
import com.arttrip.android.domain.model.favorite.BookmarkSortType
import com.arttrip.android.domain.usecase.bookmark.ClearBookmarkCountUseCase
import com.arttrip.android.domain.usecase.bookmark.GetBookmarksUseCase
import com.arttrip.android.domain.usecase.bookmark.ObserveBookmarkCountUseCase
import com.arttrip.android.presentation.bookmark.contract.BookmarkEffect
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkSort
import com.arttrip.android.presentation.bookmark.contract.BookmarkState
import com.arttrip.android.presentation.bookmark.model.BookmarkLocationFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel
    @Inject
    constructor(
        private val bookmarkStore: BookmarkStore,
        private val getBookmarksUseCase: GetBookmarksUseCase,
        private val observeBookmarkCountUseCase: ObserveBookmarkCountUseCase,
        private val clearBookmarkCountUseCase: ClearBookmarkCountUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(BookmarkState())
        val state: StateFlow<BookmarkState> = _state

        private val _effect = MutableSharedFlow<BookmarkEffect>()
        val effect: SharedFlow<BookmarkEffect> = _effect

        private val sortTypeFlow = MutableStateFlow(BookmarkSortType.LATEST)
        private val filterParamsFlow: MutableStateFlow<Pair<List<String>?, List<String>?>> =
            MutableStateFlow(listOf(ForeignCountry.Entire.label) to listOf(DomesticRegion.Entire.label))

        @OptIn(ExperimentalCoroutinesApi::class)
        val bookmarksFlow: Flow<PagingData<Bookmark>> =
            combine(sortTypeFlow, filterParamsFlow) { sortType, filterParams ->
                sortType to filterParams
            }.flatMapLatest { (sortType, filterParams) ->
                val (countries, regions) = filterParams
                getBookmarksUseCase(
                    sortType = sortType,
                    countries = countries,
                    regions = regions,
                )
            }.cachedIn(viewModelScope)

        private var bookmarkCountJob: Job? = null

        var isInitialLoad: Boolean = true
            private set

        init {
            initialize()
        }

        fun onResumed() {
            if (isInitialLoad) isInitialLoad = false
        }

        private fun initialize() {
            clearBookmarkCountUseCase()
            _state.update { it.copy(bookmarkTotalCount = null) }

            bookmarkCountJob?.cancel()
            bookmarkCountJob =
                viewModelScope.launch {
                    observeBookmarkCountUseCase().collectLatest { count ->
                        _state.update { it.copy(bookmarkTotalCount = count) }
                    }
                }
        }

        fun onIntent(intent: BookmarkIntent) {
            when (intent) {
                is BookmarkIntent.ChangeSort -> {
                    _state.update { it.copy(sort = intent.sort) }
                    sortTypeFlow.value = intent.sort.toSortType()
                }
                is BookmarkIntent.ClickItem -> {
                    viewModelScope.launch { _effect.emit(BookmarkEffect.NavigateToDetail(intent.exhibitId)) }
                }
                is BookmarkIntent.ToggleBookmark -> {
                    bookmarkStore.toggle(intent.exhibitId)
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
                    val filter = cur.editingLocationFilter
                    val countries = filter.foreignCountries.map { it.label }
                    val regions = filter.domesticRegions.map { it.label }
                    _state.update { s ->
                        s.copy(
                            isFilterSheetVisible = false,
                            appliedLocationFilter = s.editingLocationFilter,
                        )
                    }
                    filterParamsFlow.value = countries to regions
                }
            }
        }

        fun bookmarkedFlow(exhibitId: Int): Flow<Boolean> = bookmarkStore.bookmarkedFlow(exhibitId)

        private fun BookmarkSort.toSortType(): BookmarkSortType =
            when (this) {
                BookmarkSort.LATEST -> BookmarkSortType.LATEST
                BookmarkSort.DEADLINE -> BookmarkSortType.ENDING_SOON
            }

        fun setBookmarkFromRemote(
            exhibitId: Int,
            isBookmarked: Boolean,
        ) = bookmarkStore.setFromRemote(exhibitId, isBookmarked)

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
