package com.arttrip.android.presentation.bookmark.contract

import com.arttrip.android.domain.model.exhibition.ExhibitionDetailModel

data class BookmarkState(
    val isLoading: Boolean = false,
    val sort: BookmarkSort = BookmarkSort.LATEST,
    val bookmarkList: List<ExhibitionDetailModel> = emptyList(),
    val bookmarkedMap: Map<Int, Boolean> = emptyMap(),
)

enum class BookmarkSort {
    LATEST,
    DEADLINE,
}
