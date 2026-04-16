package com.arttrip.app.presentation.home.sub.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.exhibition.GetSearchExhibitionUseCase
import com.arttrip.app.domain.usecase.search.DeleteAllRecentSearchUseCase
import com.arttrip.app.domain.usecase.search.DeleteRecentSearchUseCase
import com.arttrip.app.domain.usecase.search.GetRecentSearchUseCase
import com.arttrip.app.domain.usecase.search.GetRecommendKeywordUseCase
import com.arttrip.app.presentation.home.sub.search.contract.SearchEffect
import com.arttrip.app.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.app.presentation.home.sub.search.contract.SearchState
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
        private val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
        private val getRecommendKeywordUseCase: GetRecommendKeywordUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(SearchState())
        val state: StateFlow<SearchState> = _state

        private val _effect = MutableSharedFlow<SearchEffect>()
        val effect: SharedFlow<SearchEffect> = _effect

        private val searchTrigger = MutableSharedFlow<String>()

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitions =
            searchTrigger
                .flatMapLatest { keyword ->
                    getSearchExhibitionUseCase(keyword)
                }.cachedIn(viewModelScope)

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
                        searchTrigger.emit(intent.keyword)
                    }
                }
                is SearchIntent.RecentKeywordClicked -> {
                    viewModelScope.launch {
                        _state.update { it.copy(inputText = intent.keyword, isSearchResultVisible = true) }
                        searchTrigger.emit(intent.keyword)
                    }
                }
                is SearchIntent.RecentKeywordDismissClicked -> {
                    deleteRecentSearch(intent.id)
                }
                is SearchIntent.RecommendKeywordClicked -> {
                    viewModelScope.launch {
                        _state.update { it.copy(inputText = intent.keyword, isSearchResultVisible = true) }
                        searchTrigger.emit(intent.keyword)
                    }
                }
                SearchIntent.DeleteAllClicked -> {
                    deleteAllRecentSearch()
                }
                is SearchIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(SearchEffect.NavigateToDetail(intent.id))
                    }
                }
                is SearchIntent.LikeClicked -> {}
            }
        }

        private fun deleteAllRecentSearch() {
            viewModelScope.launch {
                deleteAllRecentSearchUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> Unit
                        is ApiResult.Success -> _state.update { it.copy(recentKeywordList = emptyList()) }
                        is ApiResult.Error -> Unit
                    }
                }
            }
        }

        private fun deleteRecentSearch(id: Int) {
            viewModelScope.launch {
                deleteRecentSearchUseCase(id).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> Unit
                        is ApiResult.Success ->
                            _state.update {
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
