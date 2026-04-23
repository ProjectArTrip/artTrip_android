package com.arttrip.app.presentation.home.sub.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.core.util.bookmark.BookmarkStore
import com.arttrip.app.domain.usecase.exhibition.GetGenreExhibitionUseCase
import com.arttrip.app.presentation.home.sub.genre.contract.GenreEffect
import com.arttrip.app.presentation.home.sub.genre.contract.GenreIntent
import com.arttrip.app.presentation.home.sub.genre.contract.GenreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel
    @Inject
    constructor(
        private val getGenreExhibitionUseCase: GetGenreExhibitionUseCase,
        private val bookmarkStore: BookmarkStore,
    ) : ViewModel() {
        private val _state = MutableStateFlow(GenreState())
        val state: StateFlow<GenreState> = _state

        val bookmarked = bookmarkStore.bookmarked

        private val _effect = MutableSharedFlow<GenreEffect>()
        val effect: SharedFlow<GenreEffect> = _effect

        private val genreTrigger = MutableSharedFlow<GenreQueryParams>(replay = 1)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitions: kotlinx.coroutines.flow.Flow<PagingData<Exhibition>> =
            genreTrigger
                .flatMapLatest { params ->
                    getGenreExhibitionUseCase(
                        country = params.country,
                        genre = params.genre,
                        sortType = params.sortType,
                        onTotalCountLoaded = { count ->
                            _state.update { it.copy(exhibitTotalCount = count) }
                        },
                    )
                }.cachedIn(viewModelScope)

        fun onIntent(intent: GenreIntent) {
            when (intent) {
                is GenreIntent.Initialize -> {
                    val alreadyInitialized = _state.value.selectedGenre != null
                    _state.update {
                        if (alreadyInitialized) return@update it
                        it.copy(country = intent.country, selectedGenre = intent.genre)
                    }
                    if (!alreadyInitialized) {
                        emitTrigger(intent.country, intent.genre, _state.value.selectedSortType)
                    }
                }
                GenreIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(GenreEffect.NavigateBack)
                    }
                }
                GenreIntent.NotificationIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(GenreEffect.NavigateToNotification)
                    }
                }
                GenreIntent.OpenFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = true) }
                }
                GenreIntent.CloseFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = false) }
                }
                is GenreIntent.SelectSortType -> {
                    _state.update { it.copy(selectedSortType = intent.type) }
                }
                is GenreIntent.SelectGenre -> {
                    _state.update { it.copy(selectedGenre = intent.genre) }
                    val current = _state.value
                    emitTrigger(current.country, intent.genre, current.selectedSortType)
                }
                is GenreIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(GenreEffect.NavigateToDetail(intent.id))
                    }
                }
                is GenreIntent.LikeClicked -> {
                    bookmarkStore.toggle(intent.id)
                }
            }
        }

        private fun emitTrigger(
            country: ForeignCountry?,
            genre: ExhibitionGenre,
            sortType: SortType,
        ) {
            viewModelScope.launch {
                genreTrigger.emit(GenreQueryParams(country = country, genre = genre, sortType = sortType))
            }
        }

        private data class GenreQueryParams(
            val country: ForeignCountry?,
            val genre: ExhibitionGenre,
            val sortType: SortType,
        )
    }
