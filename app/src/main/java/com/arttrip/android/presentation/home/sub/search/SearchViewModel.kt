package com.arttrip.android.presentation.home.sub.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.exhibition.GetSearchExhibitionUseCase
import com.arttrip.android.domain.usecase.search.DeleteRecentSearchUseCase
import com.arttrip.android.domain.usecase.search.GetRecentSearchUseCase
import com.arttrip.android.domain.usecase.search.GetRecommendKeywordUseCase
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
        private val getSearchExhibitionUseCase: GetSearchExhibitionUseCase,
        private val getRecentSearchUseCase: GetRecentSearchUseCase,
        private val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
        private val getRecommendKeywordUseCase: GetRecommendKeywordUseCase,
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

        init {
            loadRecentSearch()
            loadRecommendKeywords()
        }

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
                    viewModelScope.launch {
                        _state.update { it.copy(inputText = intent.keyword, isSearchResultVisible = true) }
                        _searchTrigger.emit(intent.keyword)
                    }
                }
                is SearchIntent.RecentKeywordDismissClicked -> {
                    deleteRecentSearch(intent.id)
                }
                is SearchIntent.RecommendKeywordClicked -> {
                    viewModelScope.launch {
                        _state.update { it.copy(inputText = intent.keyword, isSearchResultVisible = true) }
                        _searchTrigger.emit(intent.keyword)
                    }
                }
                SearchIntent.DeleteAllClicked -> {
                    _state.update { it.copy(recentKeywordList = emptyList()) }
                }
                is SearchIntent.ExhibitionClicked -> {}
                is SearchIntent.LikeClicked -> {}
            }
        }

        private fun deleteRecentSearch(id: Int) {
            viewModelScope.launch {
                deleteRecentSearchUseCase(id).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> Unit
                        is ApiResult.Success -> _state.update {
                            it.copy(recentKeywordList = it.recentKeywordList.filter { item -> item.id != id })
                        }
                        is ApiResult.Error -> Unit
                    }
                }
            }
        }

        private fun loadRecommendKeywords() {
            viewModelScope.launch {
                getRecommendKeywordUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> Unit
                        is ApiResult.Success -> _state.update { it.copy(recommendKeywordList = result.data) }
                        is ApiResult.Error -> Unit
                    }
                }
            }
        }

        private fun loadRecentSearch() {
            viewModelScope.launch {
                getRecentSearchUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> Unit
                        is ApiResult.Success -> _state.update { it.copy(recentKeywordList = result.data) }
                        is ApiResult.Error -> Unit
                    }
                }
            }
        }
    }