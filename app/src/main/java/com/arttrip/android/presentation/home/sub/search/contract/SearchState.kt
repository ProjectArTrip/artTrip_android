package com.arttrip.android.presentation.home.sub.search.contract

import com.arttrip.android.domain.model.recentsearch.RecentSearch

data class SearchState(
    val recentKeywordList: List<RecentSearch> = emptyList(),
    val recommendKeywordList: List<String> =
        listOf(
            "국내",
            "사진",
            "무료",
            "서울",
            "인기전시",
            "신규",
            "판교",
            "현대미술",
            "해외",
        ),
    val inputText: String = "",
    val isSearchResultVisible: Boolean = false,
)
