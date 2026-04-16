package com.arttrip.app.presentation.bookmark.contract

import com.arttrip.app.presentation.bookmark.model.BookmarkLocationFilter

data class BookmarkState(
    val isLoading: Boolean = false,
    val sort: BookmarkSort = BookmarkSort.LATEST,
    val bookmarkTotalCount: Int? = null,
    val isFilterSheetVisible: Boolean = false,
    val appliedLocationFilter: BookmarkLocationFilter = BookmarkLocationFilter(),
    val editingLocationFilter: BookmarkLocationFilter = BookmarkLocationFilter(),
) {
    val isSearchEnabled: Boolean
        get() =
            editingLocationFilter.foreignCountries.isNotEmpty() ||
                editingLocationFilter.domesticRegions.isNotEmpty()
    val isEmpty: Boolean
        get() = bookmarkTotalCount == 0
}

enum class BookmarkSort {
    LATEST,
    DEADLINE,
}
