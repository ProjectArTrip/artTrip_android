package com.arttrip.android.presentation.home.sub.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arttrip.android.domain.usecase.exhibition.GetSearchExhibitionUseCase
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import com.arttrip.android.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.android.presentation.home.sub.search.contract.SearchState
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
class SearchViewModel
    @Inject
    constructor(
        private val getSearchExhibitionUseCase: GetSearchExhibitionUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow(SearchState())
        val state: StateFlow<SearchState> = _state

        private val _effect = MutableSharedFlow<SearchEffect>()
        val effect: SharedFlow<SearchEffect> = _effect

    private val _searchTrigger = MutableSharedFlow<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val exhibitions =
        _searchTrigger
            .flatMapLatest { keyword ->
                getSearchExhibitionUseCase(keyword)
            }
            .cachedIn(viewModelScope)

        fun onIntent(intent: SearchIntent) {
            when (intent) {
                SearchIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(SearchEffect.NavigateBack)
                    }
                }
                is SearchIntent.InputTextChanged -> {
                    _state.update { it.copy(inputText = intent.text) }
                }
                is SearchIntent.SearchClicked -> {
                    viewModelScope.launch {
                        _state.update { it.copy(isSearchResultVisible = true) }
                        _searchTrigger.emit(intent.keyword)
                    }
                }
                is SearchIntent.RecentKeywordClicked -> {
                }
                is SearchIntent.RecentKeywordDismissClicked -> {
                    _state.update {
                        it.copy(
                            recentKeywordList = it.recentKeywordList - intent.keyword,
                        )
                    }
                }
                is SearchIntent.RecommendKeywordClicked -> {}
                SearchIntent.DeleteAllClicked -> {
                    _state.update { it.copy(recentKeywordList = emptyList()) }
                }
                is SearchIntent.ExhibitionClicked -> {}
                is SearchIntent.LikeClicked -> {}
            }
        }
    }
