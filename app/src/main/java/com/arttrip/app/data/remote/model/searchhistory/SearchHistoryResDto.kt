package com.arttrip.app.data.remote.model.searchhistory

data class SearchHistoryResponseDto(
    val items: List<SearchHistoryItemDto>,
)

data class SearchHistoryItemDto(
    val searchHistoryId: Int,
    val content: String,
    val createdAt: String,
)
