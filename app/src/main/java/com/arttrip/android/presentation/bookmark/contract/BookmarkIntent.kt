package com.arttrip.android.presentation.bookmark.contract

sealed interface BookmarkIntent {
    data class ChangeSort(
        val sort: BookmarkSort,
    ) : BookmarkIntent
}
