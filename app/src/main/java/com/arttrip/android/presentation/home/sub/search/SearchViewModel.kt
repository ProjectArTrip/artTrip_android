package com.arttrip.android.presentation.home.sub.search

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import com.arttrip.android.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.android.presentation.home.sub.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(SearchState())
        val state: StateFlow<SearchState> = _state

        private val _effect = MutableSharedFlow<SearchEffect>()
        val effect: SharedFlow<SearchEffect> = _effect

        fun onIntent(intent: SearchIntent) {
            when (intent) {
                is SearchIntent.RecentKeywordClicked -> TODO()
                is SearchIntent.RecentKeywordDismissClicked -> TODO()
                is SearchIntent.RecommendKeywordClicked -> TODO()
                SearchIntent.DeleteAllClicked -> TODO()
                is SearchIntent.ExhibitionClicked -> TODO()
                is SearchIntent.LikeClicked -> TODO()
            }
        }
    }
