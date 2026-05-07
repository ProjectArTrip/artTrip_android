package com.arttrip.app.presentation.home.sub.curation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.core.util.bookmark.BookmarkStore
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.usecase.exhibition.GetCurationExhibitionListUseCase
import com.arttrip.app.presentation.home.sub.curation.contract.CurationEffect
import com.arttrip.app.presentation.home.sub.curation.contract.CurationIntent
import com.arttrip.app.presentation.home.sub.curation.contract.CurationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurationViewModel
    @Inject
    constructor(
        private val getCurationExhibitionListUseCase: GetCurationExhibitionListUseCase,
        private val bookmarkStore: BookmarkStore,
    ) : ViewModel() {
        private val _state = MutableStateFlow(CurationState())
        val state: StateFlow<CurationState> = _state

        val bookmarked = bookmarkStore.bookmarked

        private val _effect = MutableSharedFlow<CurationEffect>()
        val effect: SharedFlow<CurationEffect> = _effect

        private val curationTrigger = MutableSharedFlow<Long>(replay = 1)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitions: Flow<PagingData<Exhibition>> =
            curationTrigger
                .flatMapLatest { curationId ->
                    getCurationExhibitionListUseCase(
                        curationId = curationId,
                        onTitleLoaded = { title ->
                            _state.update { it.copy(title = title) }
                        },
                    )
                }.cachedIn(viewModelScope)

        fun onIntent(intent: CurationIntent) {
            when (intent) {
                is CurationIntent.Initialize -> {
                    if (_state.value.isInitialized) return
                    _state.update { it.copy(isInitialized = true) }
                    viewModelScope.launch {
                        curationTrigger.emit(intent.curationId)
                    }
                }
                CurationIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(CurationEffect.NavigateBack)
                    }
                }
                CurationIntent.NotificationIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(CurationEffect.NavigateToNotification)
                    }
                }
                is CurationIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(CurationEffect.NavigateToDetail(intent.id))
                    }
                }
                is CurationIntent.LikeClicked -> {
                    bookmarkStore.toggle(intent.id)
                }
            }
        }
    }
