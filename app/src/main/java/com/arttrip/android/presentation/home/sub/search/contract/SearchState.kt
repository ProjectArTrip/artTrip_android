package com.arttrip.android.presentation.home.sub.search.contract

import com.arttrip.android.domain.model.recentsearch.RecentSearch
import com.arttrip.android.domain.model.usertaste.Taste

data class SearchState(
    val recentKeywordList: List<RecentSearch> = emptyList(),
    val recommendKeywordList: List<Taste> = emptyList(),
    val inputText: String = "",
    val isSearchResultVisible: Boolean = false,
)