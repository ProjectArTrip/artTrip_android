package com.arttrip.android.presentation.home.sub.search.contract

sealed interface SearchIntent {
    data class InputTextChanged(
        val text: String,
    ): SearchIntent

    data class SearchClicked(
        val keyword: String
    ): SearchIntent

    data class RecentKeywordClicked(
        val keyword: String,
    ) : SearchIntent

    data class RecentKeywordDismissClicked(
        val keyword: String,
    ) : SearchIntent

    data class RecommendKeywordClicked(
        val keyword: String,
    ) : SearchIntent

    object DeleteAllClicked : SearchIntent

    data class ExhibitionClicked(
        val id: Int,
    ) : SearchIntent

    data class LikeClicked(
        val id: Int,
    ) : SearchIntent
}
