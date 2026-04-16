package com.arttrip.app.presentation.home.sub.search.contract

import com.arttrip.app.domain.model.recentsearch.RecentSearch
import com.arttrip.app.domain.model.usertaste.Taste

data class SearchState(
    val recentKeywordList: List<RecentSearch> = emptyList(),
    val recommendKeywordList: List<Taste> = emptyList(),
    val inputText: String = "",
    val isSearchResultVisible: Boolean = false,
)
